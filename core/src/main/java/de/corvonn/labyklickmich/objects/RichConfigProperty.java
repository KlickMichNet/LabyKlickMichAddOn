package de.corvonn.labyklickmich.objects;

import de.corvonn.labyklickmich.GameModeRegistry;
import de.corvonn.labyklickmich.Main;
import de.corvonn.labyklickmich.utils.Icons;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.notification.Notification;
import org.jetbrains.annotations.Nullable;


public class RichConfigProperty<T> extends ConfigProperty<T> {
    private long lastSwitch = 0;
    private int switchCooldown;
    private Runnable changeListener;

    public RichConfigProperty(T value, int switchCooldown, @Nullable Runnable changeListener) {
        super(value);
        this.switchCooldown = switchCooldown;
        this.changeListener = changeListener;
    }

    @Override
    public void set(T value) { //TODO: Das kann man doch bestimmt optimieren
        T oldValue = super.value;
        super.set(value);

        boolean fireEvent = false;

        GameModeRegistry gameModeRegistry = Main.getGameModeRegistry();
        if(gameModeRegistry != null && gameModeRegistry.getCurrentGameMode() != null && switchCooldown > 0) {
            if(System.currentTimeMillis() - lastSwitch < switchCooldown) {
                Laby.labyAPI().notificationController().push(
                        Notification.builder()
                                .title(Component.translatable("labyklickmich.notification.switch.cooldown.title"))
                                .text(Component.translatable("labyklickmich.notification.switch.cooldown.text"))
                                .icon(Icons.WARNING)
                                .build()
                );

                super.set(oldValue);
            }else {
                lastSwitch = System.currentTimeMillis();
                fireEvent = true;
            }
        } else fireEvent = true;

        if(fireEvent && changeListener != null) changeListener.run();
    }
}
