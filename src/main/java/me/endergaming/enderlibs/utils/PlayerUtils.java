package me.endergaming.enderlibs.utils;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        // TODO -> Bukkit hide players but keep tablist packets
        hidePlayer(toHide);
    }

    public static void showPlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(other -> other.showPlayer(player));
    }
}
