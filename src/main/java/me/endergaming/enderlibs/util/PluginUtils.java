package me.endergaming.enderlibs.util;

import me.endergaming.enderlibs.EnderLibs;
import me.endergaming.enderlibs.text.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PluginUtils {
    /**
     * Returns every player that has joined this server in the past.
     *
     * @return List of OfflinePlayer objects that have connected to the server.
     */
    public static List<OfflinePlayer> getOfflinePlayerList() {
        return Arrays.asList(EnderLibs.getInstance().getServer().getOfflinePlayers());
    }

    /**
     * Returns every player's UUID that has joined this server in the past.
     *
     * @return List of UUID objects originating from the OfflinePlayer objects that have connected to the server.
     */
    public static List<UUID> getOfflinePlayerIdList() {
        return getOfflinePlayerList().stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toList());
    }

    /**
     * Returns every player's name that has joined this server in the past.
     *
     * @return List of String objects originating from the OfflinePlayer objects that have connected to the server.
     */
    public static List<String> getOfflinePlayerNameList() {
        return getOfflinePlayerList().stream().map(OfflinePlayer::getName).collect(Collectors.toList());
    }

    /**
     * Returns every player that is currently connected to the server.
     *
     * @return List of Player objects that are currently connected to the server.
     */
    public static List<? extends Player> getOnlinePlayerList() {
        return new ArrayList<>(EnderLibs.getInstance().getServer().getOnlinePlayers());
    }

    /**
     * Returns every player's UUID that is currently connected to the server.
     *
     * @return List of UUID objects originating from the Player objects that are currently connected to the server.
     */
    public static List<UUID> getOnlinePlayerIdList() {
        return getOnlinePlayerList().stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toList());
    }

    /**
     * Returns every player's name that is currently connected to the server.
     *
     * @return List of String objects originating from the Player objects that are currently connected to the server.
     */
    public static List<String> getOnlinePlayerNameList() {
        return getOnlinePlayerList().stream().map(Player::getName).collect(Collectors.toList());
    }

    /**
     * Disable plugin with a custom log message.
     *
     * @param message Custom log message displayed before plugin is disabled
     */
    public static void disablePlugin(String message) {
        MessageUtils.log(MessageUtils.LogLevel.SEVERE, message);
        EnderLibs.getInstance().getPluginLoader().disablePlugin(EnderLibs.getInstance());
    }

}
