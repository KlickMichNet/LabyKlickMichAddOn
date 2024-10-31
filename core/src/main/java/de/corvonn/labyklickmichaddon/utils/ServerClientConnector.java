package de.corvonn.labyklickmichaddon.utils;

import de.corvonn.labyklickmichaddon.gameMode.AbstractGameMode;
import de.corvonn.labyklickmichaddon.Main;
import de.corvonn.labyklickmichaddon.labyModPackets.GameModePacket;
import de.corvonn.labyklickmichaddon.labyModPackets.PerformActionPacket;
import de.corvonn.labyklickmichaddon.labyModPackets.RankingPacket;
import de.corvonn.labyklickmichaddon.labyModPackets.SettingsPacket;
import net.labymod.api.Laby;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.serverapi.api.packet.Direction;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.AddonProtocol;

import java.util.UUID;

public class ServerClientConnector {
    private final AddonProtocol protocol;


    public ServerClientConnector() {
        LabyModProtocolService service = Laby.references().labyModProtocolService();
        protocol = new AddonProtocol(service,"labyklickmich");
        service.registry().registerProtocol(protocol);
        registerPackets();
    }

    private void registerPackets() {
        protocol.registerPacket(
                GameModePacket.PACKET_ID,
                GameModePacket.class,
                Direction.CLIENTBOUND, //TODO: Potentieller API-Bug - hier müsste eigentlich BOTH stehen
                Main.getGameModeRegistry()::onGameModePacketReceived
        );

        protocol.registerPacket(
                PerformActionPacket.PACKET_ID,
                PerformActionPacket.class,
                Direction.SERVERBOUND
        );

        protocol.registerPacket(
                SettingsPacket.PACKET_ID,
                SettingsPacket.class,
                Direction.SERVERBOUND
        );

        protocol.registerPacket(
            RankingPacket.PACKET_ID,
            RankingPacket.class,
            Direction.CLIENTBOUND,
            (uuid, rankingPacket) -> {
                AbstractGameMode currentGameMode = Main.getGameModeRegistry().getCurrentGameMode();
                if(currentGameMode != null) currentGameMode.onRankingPacketReceived(rankingPacket);
            }
        );
    }

    public void sendPacket(UUID receiver, Packet packet) {
        protocol.sendPacket(receiver, packet);
    }

    public void sendServerPacket(Packet packet) {
        sendPacket(UUID.nameUUIDFromBytes(new byte[]{0, 0}), packet);
    }
}
