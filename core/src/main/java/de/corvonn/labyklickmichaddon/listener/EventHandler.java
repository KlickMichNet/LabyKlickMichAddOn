package de.corvonn.labyklickmichaddon.listener;

import de.corvonn.labyklickmichaddon.gameMode.AbstractGameMode;
import de.corvonn.labyklickmichaddon.Main;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerSwitchEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;

public class EventHandler {

    @Subscribe
    public void onServerSwitch(ServerSwitchEvent event) {
        Main.getGameModeRegistry().disableGameModeWhenEnabled(AbstractGameMode.DisableReason.LEFT_GAME_MODE);
    }

    @Subscribe
    public void onSubServerSwitch(SubServerSwitchEvent event) {
        Main.getGameModeRegistry().disableGameModeWhenEnabled(AbstractGameMode.DisableReason.LEFT_GAME_MODE);
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        Main.getGameModeRegistry().disableGameModeWhenEnabled(AbstractGameMode.DisableReason.LEFT_GAME_MODE);
    }
}
