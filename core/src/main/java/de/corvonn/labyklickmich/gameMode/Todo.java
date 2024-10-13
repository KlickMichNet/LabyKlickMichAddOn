package de.corvonn.labyklickmich.gameMode;

import de.corvonn.labyklickmich.Configuration;
import de.corvonn.labyklickmich.Main;
import de.corvonn.labyklickmich.labyModPackets.PerformActionPacket;
import de.corvonn.labyklickmich.labyModPackets.RankingPacket;
import de.corvonn.labyklickmich.labyModPackets.SettingsPacket;
import de.corvonn.labyklickmich.objects.GameMode;
import de.corvonn.labyklickmich.objects.widgets.hud.AddonHudWidgetRegistry;
import de.corvonn.labyklickmich.objects.widgets.hud.LeaderBoardHudWidget;
import de.corvonn.labyklickmich.objects.widgets.hud.RankingHudWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.HotkeyService;

public class Todo extends AbstractGameMode {
    private final static String BACKPACK_HOTKEY_IDENTIFIER = "klickmich_todo_backpack";

    public Todo() {
        super(GameMode.TODO);
        Laby.references().hotkeyService().register(
                BACKPACK_HOTKEY_IDENTIFIER,
                Main.getInstance().configuration().getBackpackHotkey(),
                () -> HotkeyService.Type.TOGGLE,
                this::handleBackPackHotkeyCall
        );

        sendNewSettings();
    }


    @Override
    public void disable() {
        Laby.references().hotkeyService().unregister(BACKPACK_HOTKEY_IDENTIFIER);

        AddonHudWidgetRegistry hudWidgetRegistry = Main.getAddonHudWidgetRegistry();
        hudWidgetRegistry.getRankingHudWidget().visibility(false);
        hudWidgetRegistry.getLeaderBoardHudWidget().visibility(false);
    }

    private void handleBackPackHotkeyCall(boolean b) {
        Main.getServerClientConnector().sendServerPacket(new PerformActionPacket(PerformActionPacket.Action.OPEN_BACKPACK));
    }

    public void sendNewSettings() {
        Configuration config = Main.getInstance().configuration();

        Main.getServerClientConnector().sendServerPacket(
            new SettingsPacket(
                config.disableDefaultBackpackHotkey().getOrDefault(),
                config.hideLeaderboard().getOrDefault()
                )
        );
    }

    @Override
    public void onRankingPacketReceived(RankingPacket rankingPacket) {
        RankingHudWidget rw = Main.getAddonHudWidgetRegistry().getRankingHudWidget();
        rw.setNewRank(rankingPacket.getCurrentRank());
        rw.visibility(true);

        LeaderBoardHudWidget lw = Main.getAddonHudWidgetRegistry().getLeaderBoardHudWidget();
        lw.updateLeaderBoardWithServerAPIComponent(rankingPacket.getRankingList());
        lw.visibility(true);
    }
}
