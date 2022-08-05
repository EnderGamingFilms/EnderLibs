package me.endergaming.enderlibs.nms.packets;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface IPacketSender {
    void sendPacket(PlayerConnection conn, Packet<?> packet) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

    PacketPlayOutPlayerInfo.PlayerInfoData getPlayerInfo(Player player) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException;
}
