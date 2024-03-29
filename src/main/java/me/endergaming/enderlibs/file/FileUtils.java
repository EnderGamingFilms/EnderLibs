package me.endergaming.enderlibs.file;

import me.endergaming.enderlibs.misc.ServerUtils;
import me.endergaming.enderlibs.text.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtils {
    /**
     * Returns the Config located at the "path" with extension "ext"
     * <br /><br />
     * Note: If the file does not exist it will try to be saved from the corresponding location in the plugins resources
     *
     * @param path Path to file, using "." as a separator for folders (ex: {@literal "data.players"})
     * @param ext  File extension (ex: {@literal "yml"})
     * @return org.bukkit.configuration.file.FileConfiguration
     */
    public static FileConfiguration getConfig(String path, String ext, @NotNull JavaPlugin instance) {
        return YamlConfiguration.loadConfiguration(getFile(path, ext, instance));
    }

    /**
     * Returns the Config located at the "path" with extension "ext"
     * <br /><br />
     * Note: If the file does not exist it will try to be saved from the corresponding location in the plugins resources
     *
     * @param path Path to file, using "." as a separator for folders (ex: {@literal "data.players"})
     * @param ext  File extension (ex: {@literal "yml"})
     * @return org.bukkit.configuration.file.FileConfiguration
     */
    public static FileConfiguration getConfig(String path, String ext) {
        JavaPlugin instance = (JavaPlugin) ServerUtils.getCallingPlugin();
        if (instance == null) {
            throw new NullPointerException("Obtained plugin instance was null.");
        }
        return YamlConfiguration.loadConfiguration(getFile(path, ext, instance));
    }

    /**
     * Returns the File located at the "path" with extension "ext".
     * <br /><br />
     * Note: If the file does not exist it will try to be saved from the corresponding location in the plugins resources
     *
     * @param path Path to file, using "." as a separator for folders (ex: {@literal "data.players"})
     * @param ext  File extension (ex: {@literal "yml"})
     * @return java.io.File
     */
    public static File getFile(String path, String ext) {
        JavaPlugin instance = (JavaPlugin) ServerUtils.getCallingPlugin();
        if (instance == null) {
            throw new NullPointerException("Obtained plugin instance was null.");
        }
        return getFile(path, ext, instance);
    }

    /**
     * Returns the File located at the "path" with extension "ext".
     * <br /><br />
     * Note: If the file does not exist it will try to be saved from the corresponding location in the plugins resources
     *
     * @param path Path to file, using "." as a separator for folders (ex: {@literal "data.players"})
     * @param ext  File extension (ex: {@literal "yml"})
     * @return java.io.File
     */
    public static File getFile(String path, String ext, @NotNull JavaPlugin instance) {
        String[] splitPath = path.split("\\.");
        StringBuilder parentPath = new StringBuilder().append(instance.getDataFolder().getAbsolutePath());
        String fileName = splitPath[splitPath.length - 1].concat(".").concat(ext);
        for (int i = 0; i < splitPath.length; ++i) {
            if (i != splitPath.length - 1) {
                parentPath.append(File.separator);
                parentPath.append(splitPath[i]);
            }
        }
        File file = new File(parentPath.toString(), fileName);
        if (!file.exists()) {
            try {
                instance.saveResource(path.replace(".", File.separator).concat("." + ext), true);
                MessageUtils.log(MessageUtils.LogLevel.WARNING, ChatColor.GREEN + fileName + " did not exist so one was created");
            } catch (Exception ignored) {
                try {
//                    noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
//                    noinspection ResultOfMethodCallIgnored
                    file.createNewFile();
                    MessageUtils.log(MessageUtils.LogLevel.WARNING, ChatColor.YELLOW + fileName + " did not exist so one was created");
                } catch (Exception e) {
                    MessageUtils.log(MessageUtils.LogLevel.WARNING, ChatColor.RED + "There was an issue creating " + fileName);
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
