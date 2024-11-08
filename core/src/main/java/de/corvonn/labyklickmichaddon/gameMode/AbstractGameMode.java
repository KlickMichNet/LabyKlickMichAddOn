package de.corvonn.labyklickmichaddon.gameMode;

import de.corvonn.labyklickmichaddon.labyModPackets.RankingPacket;
import de.corvonn.labyklickmichaddon.objects.GameMode;
import de.corvonn.labyklickmichaddon.utils.Icons;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;

public abstract class AbstractGameMode {

    public final GameMode gameMode;

    protected AbstractGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public final void disable(DisableReason reason) {
        if(reason == DisableReason.ADDON_DISABLED) {
            Notification.builder()
                    .title(Component.translatable("labyklickmichaddon.notification.disable_while_ingame.title"))
                    .text(Component.translatable("labyklickmichaddon.notification.disable_while_ingame.text"))
                    .icon(Icons.ERROR)
                    .duration(15000)
                    .buildAndPush();
        }
        disable();
    }

    protected abstract void disable();

    public void onRankingPacketReceived(RankingPacket rankingPacket) {
        //Standardmäßig nicht implementiert - muss ggf. vom Spielmodus implementiert werden.
    }

    public enum DisableReason {
        ADDON_DISABLED,
        LEFT_GAME_MODE,
        OTHER;
    }
}
