package de.corvonn.labyklickmichaddon.listener;

import de.corvonn.labyklickmichaddon.GameModeRegistry;
import de.corvonn.labyklickmichaddon.Main;
import de.corvonn.labyklickmichaddon.gameMode.Invasion.LowHealthEffectStrength;
import de.corvonn.labyklickmichaddon.objects.GameMode;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gfx.pipeline.post.PostProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostProcessorLoader;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.api.util.Lazy;

public class PostProcessingScreenListener {
    private static final float CHANGE_SPEED = 0.025f;
    private static final float MIN_CHANGE_SPEED = 0.001f;
    private static final int EFFECT_START = 10;
    private static final float MIN_NEGATIVE_CHANGE_SPEED = -MIN_CHANGE_SPEED;

    private static final ResourceLocation COLOR_CORRECTION = ResourceLocation.create(
        "labyklickmichaddon",
        "shaders/post/lowhealth.json"
    );

    private final Minecraft minecraft;
    private final Lazy<PostProcessor> processor;
    private final GameModeRegistry gameModeRegistry;
    private float effectStrength;
    private ConfigProperty<LowHealthEffectStrength> lowHealthGrayScaleStrength;

    public PostProcessingScreenListener(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.lowHealthGrayScaleStrength = Main.getInstance().configuration().lowHealthGrayScaleStrength();
        this.gameModeRegistry = Main.getGameModeRegistry();

        this.processor = Lazy.of(() -> {
            PostProcessor processor = PostProcessorLoader.load(
                this.minecraft.mainTarget(),
                COLOR_CORRECTION
            );

            processor.setCustomPostPassProcessor((data, command, time) -> {

                CustomPostProcessorUniformBlock colorDataBlock = data.getBlock("EffectData");
                colorDataBlock.getProperty("desaturation").set(this.effectStrength * this.lowHealthGrayScaleStrength.get().getStrength());
            });
            return processor;
        });
    }

    @Subscribe
    public void onPostProcessingScreen(PostProcessingScreenEvent event) {
        if (this.gameModeRegistry.currentGameMode() != GameMode.INVASION) return;
        if (this.lowHealthGrayScaleStrength.get() == LowHealthEffectStrength.DISABLED) return;

        this.processor.get().process(event.partialTicks());
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (this.gameModeRegistry.currentGameMode() != GameMode.INVASION) return;
        if (this.lowHealthGrayScaleStrength.get() == LowHealthEffectStrength.DISABLED ) return;

        ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        if (clientPlayer == null) return;

        float health = clientPlayer.getHealth();
        float targetStrength = 0;
        if (health < EFFECT_START && health > 0) {
            targetStrength = Math.min(1, Math.max(0, (EFFECT_START - health) / (float) EFFECT_START));
        }

        float delta = targetStrength - this.effectStrength;
        if (Math.abs(delta) <= CHANGE_SPEED) {
            this.effectStrength = targetStrength;
        } else {
            if (delta > 0) this.effectStrength += Math.max(MIN_CHANGE_SPEED, delta * CHANGE_SPEED);
            else this.effectStrength += Math.min(MIN_NEGATIVE_CHANGE_SPEED, delta * CHANGE_SPEED);
        }
    }
}
