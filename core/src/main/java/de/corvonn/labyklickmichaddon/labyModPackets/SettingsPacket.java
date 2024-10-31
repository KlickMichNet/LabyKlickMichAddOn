package de.corvonn.labyklickmichaddon.labyModPackets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

public class SettingsPacket implements Packet {
    public static final int PACKET_ID = 2;

    private boolean disableBackpackHotkey, disableLeaderboard;

    public SettingsPacket(boolean disableBackpackHotkey, boolean disableLeaderboard) {
        this.disableBackpackHotkey = disableBackpackHotkey;
        this.disableLeaderboard = disableLeaderboard;
    }

    @Override
    public void write(@NotNull PayloadWriter writer) {
        writer.writeBoolean(this.disableBackpackHotkey);
        writer.writeBoolean(this.disableLeaderboard);
    }
}
