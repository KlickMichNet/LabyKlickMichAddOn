package de.corvonn.labyklickmich;

import de.corvonn.labyklickmich.gameMode.AbstractGameMode;
import de.corvonn.labyklickmich.labyModPackets.GameModePacket;
import de.corvonn.labyklickmich.objects.GameMode;
import de.corvonn.labyklickmich.utils.Icons;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.notification.Notification;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GameModeRegistry {
    private AbstractGameMode currentGameMode = null;

    public void onGameModePacketReceived(UUID sender, GameModePacket packet) {
        if(Main.isDisabled()) return;

        disableGameModeWhenEnabled(AbstractGameMode.DisableReason.OTHER);

        GameMode mode = packet.getGameMode();
        try {
            currentGameMode = mode.getClazz().getDeclaredConstructor().newInstance();
        }catch (Exception ex) {
            Main.getInstance().logger().warn("Could not initiate GameMode: " + mode, ex);
        }

        if(currentGameMode == null) return;

        if(Main.getInstance().configuration().showSupportedGameNote().get()) {
            Notification.builder()
                .title(Component.translatable("labyklickmich.notification.welcome.title")
                    .arguments(Component.text(currentGameMode.getGameMode().getDisplayName())))
                .text(Component.translatable("labyklickmich.notification.welcome.text"))
                .icon(Icons.KLICKMICH_LOGO)
                .buildAndPush();
        }
    }

    public void disableGameModeWhenEnabled(AbstractGameMode.DisableReason reason) {
        if(currentGameMode == null) return;

        currentGameMode.disable(reason);
        currentGameMode = null;
    }

    public @Nullable AbstractGameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void requestCurrentGameMode() {
        if(currentGameMode != null) return;

        ServerController serverController = Laby.labyAPI().serverController();
        if(!serverController.isConnected()) return;
        String hostAddress = serverController.getCurrentServerData().address().getHost().toLowerCase();
        if(!hostAddress.contains("klickmich") && !hostAddress.contains("klm")) return;

        Main.getServerClientConnector().sendServerPacket(new GameModePacket(GameMode.UNKNOWN));
    }
}
