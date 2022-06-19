package me.endergaming.enderlibs.nms.packets;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PacketSender_1_19 implements IPacketSender {
    Method sendPacket = null;

    @Override
    public void sendPacket(PlayerConnection conn, Packet<?> packet) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (this.sendPacket == null) {
            this.sendPacket = conn.getClass().getMethod("a", Packet.class);
        }

        this.sendPacket.invoke(conn, packet);
    }

    Constructor<PacketPlayOutPlayerInfo.PlayerInfoData> pid;

    Method chatBase;
    Method getHandle;

    Method fA;
    Field b;

    Class<?> ppk;

    @Override
    public PacketPlayOutPlayerInfo.PlayerInfoData getPlayerInfo(Player player) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        GameProfile gameProfile = new GameProfile(player.getUniqueId(), player.getName());

        if (this.getHandle == null) {
            this.getHandle = player.getClass().getMethod("getHandle");
        }

        var entityHuman = (EntityHuman) this.getHandle.invoke(player);

        if (this.fA == null) {
            var clazz = entityHuman.getClass();

            this.fA = clazz.getMethod("fA");
        }

        if (this.ppk == null) {
            this.ppk = this.fA.getReturnType();
        }

        if (this.b == null) {
            this.b = this.ppk.getDeclaredField("b");
        }

        if (this.pid == null) {
            this.pid = PacketPlayOutPlayerInfo.PlayerInfoData.class.getConstructor(GameProfile.class, int.class, EnumGamemode.class, IChatBaseComponent.class, this.b.getType());
        }

        if (this.chatBase == null) {
            // a(@Nullable String s)
            this.chatBase = IChatBaseComponent.class.getMethod("a", String.class);
        }

        var text = this.chatBase.invoke(IChatBaseComponent.class, gameProfile.getName());

        return this.pid.newInstance(gameProfile, 0, EnumGamemode.d, text, null);
    }
}
