package me.endergaming.enderlibs.file.config;

import me.endergaming.enderlibs.EnderLibs;
import me.endergaming.enderlibs.file.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoreMessages extends Configuration {

    public static String HELP;
    public static String INVALID_PLAYER;
    public static String INVALID_ARGUMENT;
    public static String INVALID_PERMISSION;
    public static String NON_PLAYER;

    public CoreMessages(@NotNull final EnderLibs plugin) {
        super(plugin);

        FileConfiguration config = plugin.getFileManager().getConfig(this.getFileName());

        HELP = config.getString("HELP");
        INVALID_PERMISSION = config.getString("INVALID_PERMISSION");
        INVALID_PLAYER = config.getString("INVALID_PLAYER");
        INVALID_ARGUMENT = config.getString("INVALID_ARGUMENT");
        NON_PLAYER = config.getString("NON_PLAYER");
    }

    @Override
    public String getFileName() {
        return "messages.yml";
    }

    @Override
    public List<Object> getObjects() {
        return null;
    }

}
