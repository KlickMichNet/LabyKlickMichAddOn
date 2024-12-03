package de.corvonn.labyklickmichaddon;

import de.corvonn.labyklickmichaddon.gameMode.AbstractGameMode;
import de.corvonn.labyklickmichaddon.gameMode.Todo;
import de.corvonn.labyklickmichaddon.objects.RichConfigProperty;
import de.corvonn.labyklickmichaddon.utils.Icons;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.notification.Notification;

@SpriteTexture(value = "settings")
@ConfigName("settings")
public class Configuration extends AddonConfig {

    @SettingSection(value = "general")
    @SpriteSlot(size = 32)
    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(size = 32, x = 1)
    @SwitchSetting
    private final ConfigProperty<Boolean> showSupportedGameNote = new ConfigProperty<>(true);

    @SpriteSlot(size = 32, x = 2)
    @SettingSection(value = "todo")
    @SwitchSetting
    private final ConfigProperty<Boolean> hideLeaderboard = new RichConfigProperty<>(false, 1000, this::onLeaderBoardSettingsChange);

    @SpriteSlot(size = 32, x = 3)
    @SwitchSetting
    private final ConfigProperty<Boolean> disableDefaultBackpackHotkey = new RichConfigProperty<>(true, 1000, this::sendSettingsChange);

    @SpriteSlot(size = 32, y = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> backpackHotkey = new ConfigProperty<>(Key.B);

    @Exclude
    private final ConfigProperty<Integer> klickmichConfigVersion = new ConfigProperty<>(0);

    public Configuration() {
        enabled.addChangeListener(b -> {
            if(b) {
                Main.getGameModeRegistry().requestCurrentGameMode();
            }else{
                Main.getGameModeRegistry().disableGameModeWhenEnabled(AbstractGameMode.DisableReason.ADDON_DISABLED);
            }
        });
    }

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ConfigProperty<Boolean> disableDefaultBackpackHotkey() {
        return this.disableDefaultBackpackHotkey;
    }

    public ConfigProperty<Key> getBackpackHotkey() {
        return this.backpackHotkey;
    }

    public ConfigProperty<Boolean> hideLeaderboard() {
        return this.hideLeaderboard;
    }

    public ConfigProperty<Boolean> showSupportedGameNote() {
        return this.showSupportedGameNote;
    }

    public ConfigProperty<Integer> klickmichConfigVersion() {
        return this.klickmichConfigVersion;
    }

    //TODO: Sobald diese Logik von Nico gefixt wurde, wieder implementieren und das RichConfigProperty dadurch ersetzen
    //private boolean cooldownHideLeaderboard = false;

    //@SettingListener(target = "hideLeaderboard", type = EventType.INITIALIZE)
    //public void onInit(Setting setting) {
    //    if(!setting.isElement()) return;

    //    SettingElement se = setting.asElement();
    //    se.setOverlayInfo(new SettingOverlayInfo(
    //        () -> cooldownHideLeaderboard,
    //        Component.text("Lol"),
    //        false
    //    ));

    //    hideLeaderboard.addChangeListener(() -> {
    //       cooldownHideLeaderboard = true;
    //        Debounce.of("hideLeaderboardCooldown", 1000, () -> cooldownHideLeaderboard = false);
    //    });
    //}

    private void sendSettingsChange() {
        GameModeRegistry registry = Main.getGameModeRegistry();
        if(registry != null && registry.getCurrentGameMode() instanceof Todo todo) todo.sendNewSettings();
    }

    private void onLeaderBoardSettingsChange() {
        if(Main.getGameModeRegistry() == null) return;
        if(Main.getGameModeRegistry().getCurrentGameMode() instanceof Todo todo) {
            Notification.builder()
                .icon(Icons.INFO)
                .title(Component.translatable("labyklickmichaddon.notification.leaderboard_possibly_not_changed.title"))
                .text(Component.translatable("labyklickmichaddon.notification.leaderboard_possibly_not_changed.text"))
                .buildAndPush();

            todo.sendNewSettings();
        }
    }
}
