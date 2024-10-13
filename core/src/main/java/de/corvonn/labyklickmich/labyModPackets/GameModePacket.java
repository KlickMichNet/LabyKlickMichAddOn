package de.corvonn.labyklickmich.labyModPackets;

import de.corvonn.labyklickmich.GameModeRegistry;
import de.corvonn.labyklickmich.objects.GameMode;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

public class GameModePacket implements Packet {
    public static final int PACKET_ID = 0;

    private GameMode mode;

    public GameModePacket(@NotNull GameMode gameMode) {
        mode = gameMode;
    }

    public GameMode getGameMode() {
        return mode;
    }

    @Override
    public void write(@NotNull PayloadWriter writer) {
        writer.writeString(mode.getId());
    }

    @Override
    public void read(@NotNull PayloadReader reader) {
        mode = GameMode.valueOf(reader.readString());
    }
}
