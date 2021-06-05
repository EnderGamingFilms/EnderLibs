package me.endergaming.enderlibs.file;

import me.endergaming.enderlibs.EnderLibs;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;

public abstract class Configuration implements IConfiguration {

    private final EnderLibs plugin;

    public Configuration(@NotNull final EnderLibs plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() throws FileNotFoundException {
        return plugin.getFileManager().getConfig(this.getFileName());
    }

    public Object getValue(Object o) {
        for (Object object : this.getObjects()) {
            if (object.equals(o)) {
                return object;
            }
        }

        return null;
    }

}
