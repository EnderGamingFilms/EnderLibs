package me.endergaming.enderlibs.misc;

import me.endergaming.enderlibs.nms.packets.IPacketSender;
import me.endergaming.enderlibs.nms.packets.PacketSender_1_17;
import me.endergaming.enderlibs.nms.packets.PacketSender_1_18;
import me.endergaming.enderlibs.nms.packets.PacketSender_1_19;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerUtils {
    static IPacketSender packetSender = null;

    public static void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setSaturation(20);
        player.setFoodLevel(20);
        player.setVisualFire(false);
        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setExhaustion(0);
    }

    public static void hidePlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(other -> other.hidePlayer(player));
    }

    public static void hidePlayer(Player player, String exemptPerm) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            // Do not hide player from admins/spectators
            if (other.hasPermission(exemptPerm)) {
                continue;
            }

            other.hidePlayer(player);
        }
    }

    public static void hidePlayerNotOnTab(Player toHide) {
        if (packetSender == null) {
            var ver = ServerUtils.getServerVersion();

            if (ver.contains("1.17")) {
                packetSender = new PacketSender_1_17();
            } else if (ver.contains("1.18")) {
                packetSender = new PacketSender_1_18();
            } else if (ver.contains("1.19")) {
                packetSender = new PacketSender_1_19();
            }
        }

        hidePlayer(toHide);
        // When a player is hidden (not on tab) their tab name will be shaded as if they were in spectator mode.
        PacketPlayOutPlayerInfo.PlayerInfoData playerInfo = null;

        try {
            playerInfo = packetSender.getPlayerInfo(toHide);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        var packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a);

        packet.b().add(playerInfo);

        Bukkit.getOnlinePlayers().stream().filter(p -> !p.getUniqueId().equals(toHide.getUniqueId())).forEach(player -> {
            try {
                PlayerConnection conn = (PlayerConnection) getConnection(player);

                packetSender.sendPacket(conn, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void showPlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(other -> other.showPlayer(player));
    }

    static Method getHandle = null;
    static Field conField = null;

    private static Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (getHandle == null) {
            getHandle = player.getClass().getMethod("getHandle");
        }

        var nmsPlayer = getHandle.invoke(player);

        if (conField == null) {
            // Obtain obfuscated player connection
            conField = nmsPlayer.getClass().getField("b");
        }

        return conField.get(nmsPlayer);
    }
}
