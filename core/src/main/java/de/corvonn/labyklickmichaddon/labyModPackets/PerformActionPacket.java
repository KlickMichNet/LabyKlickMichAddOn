package de.corvonn.labyklickmichaddon.labyModPackets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

public class PerformActionPacket implements Packet {
    public static final int PACKET_ID = 1;

    private Action action;

    public PerformActionPacket(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public void write(@NotNull PayloadWriter writer) {
        writer.writeString(action.name());
    }

    @Override
    public void read(@NotNull PayloadReader reader) {
        action = Action.valueOf(reader.readString());
    }


    public enum Action {
        OPEN_BACKPACK;
    }
}
