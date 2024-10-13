
package de.corvonn.labyklickmich.labyModPackets;

import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RankingPacket implements Packet {
    public static final int PACKET_ID = 3;

    private List<ServerAPIComponent> rankingList;
    private int currentRank;

    public RankingPacket(@NotNull List<ServerAPIComponent> ranking, int currentRank) {
        this.rankingList = ranking;
        this.currentRank = currentRank;
    }

    public List<ServerAPIComponent> getRankingList() {
        return rankingList;
    }

    public int getCurrentRank() {
        return currentRank;
    }

    @Override
    public void write(@NotNull PayloadWriter writer) {
        writer.writeInt(currentRank);
        writer.writeCollection(rankingList, writer::writeComponent);
    }

    @Override
    public void read(@NotNull PayloadReader reader) {
        currentRank = reader.readInt();
        rankingList = reader.readList(reader::readComponent);
    }
}
