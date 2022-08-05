package me.endergaming.enderlibs.nms.packets;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PacketSender_1_17 implements IPacketSender {
    Method sendPacket = null;

    @Override
    public void sendPacket(PlayerConnection conn, Packet<?> packet) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (this.sendPacket == null) {
            this.sendPacket = conn.getClass().getMethod("sendPacket", Packet.class);
        }

        this.sendPacket.invoke(conn, packet);
    }

    Constructor<PacketPlayOutPlayerInfo.PlayerInfoData> pid;
    Method chatBase;

    @Override
    public PacketPlayOutPlayerInfo.PlayerInfoData getPlayerInfo(Player player) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var gameProfile = new GameProfile(player.getUniqueId(), player.getName());

        if (this.pid == null) {
            this.pid = PacketPlayOutPlayerInfo.PlayerInfoData.class.getConstructor(GameProfile.class, int.class, EnumGamemode.class, IChatBaseComponent.class);
        }

        if (this.chatBase == null) {
            // a(@Nullable String s)
            this.chatBase = IChatBaseComponent.class.getMethod("a", String.class);
        }

        var text = this.chatBase.invoke(IChatBaseComponent.class, gameProfile.getName());

        return this.pid.newInstance(gameProfile, 0, EnumGamemode.d, text);
    }
}
