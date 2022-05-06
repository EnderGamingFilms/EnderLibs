package me.endergaming.enderlibs.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerUtils {
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
        hidePlayer(toHide);
        GameProfile gameProfile = new GameProfile(toHide.getUniqueId(), toHide.getName());
        IChatBaseComponent text = new ChatComponentText(gameProfile.getName());

        // When a player is hidden (not on tab) their tab name will be shaded as if they were in spectator mode.
        PacketPlayOutPlayerInfo.PlayerInfoData playerInfo = new PacketPlayOutPlayerInfo.PlayerInfoData(gameProfile, 0, EnumGamemode.d, text);

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a);

        packet.b().add(playerInfo);

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                PlayerConnection conn = (PlayerConnection) getConnection(player);
                conn.a.a(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
