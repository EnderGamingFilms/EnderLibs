package me.endergaming.enderlibs.file;

import me.endergaming.enderlibs.EnderLibs;
import me.endergaming.enderlibs.text.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static me.endergaming.enderlibs.EnderLibs.getCallingPlugin;

public class FileManager {

    private final HashMap<String, File> fileList = new HashMap<>();
    private final File dataFolder;
    private final JavaPlugin plugin;

    public FileManager(@NotNull final JavaPlugin plugin) {
        this.dataFolder = plugin.getDataFolder();
        this.plugin = plugin;

        // Makes the plugin directory if it didn't make itself for unknown reason.
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
    }

    /**
     * Registers a configuration into the test.FileManager based upon the provided file name.
     *
     * @param   files    The names of the specified files.
     */
    public void registerConfig(String... files) {
        for (String name : files) {
            fileList.put(name, this.getFile(name));
        }
    }

    /**
     * Reloads the specific configuration to be updated for the server.
     *
     * @param   fileConfiguration       The targeted FileConfiguration you wish to reload.
     */
    // TODO: Find a better purpose or remove this method
    public void reloadConfig(FileConfiguration fileConfiguration) {
        YamlConfiguration.loadConfiguration(this.getFile(fileConfiguration.getName()));
    }

    /**
     * Returns the FileConfiguration that matches files with the specified file name.
     *
     * @param   name                    The name of the file you wish to get.
     * @return                          The FileConfiguration with the matching file name.
     */
    public FileConfiguration getConfig(String name) {
        return YamlConfiguration.loadConfiguration(this.getFile(name));
    }

    /**
     * Returns the File that's name matches the specified name.
     *
     * @param   name                    The name of the file you wish to get.
     * @return                          The file with the matching name.
     */
    public File getFile(String name) {
        File file = new File(dataFolder, name);
        plugin.getName();
        String prefix = ChatColor.WHITE + plugin.getName() + "? ";

        if (!file.exists()) {
            try {
                plugin.saveResource(name, true);
                MessageUtils.log(MessageUtils.LogLevel.WARNING, ChatColor.GREEN + name + " did not exist so one was created", prefix);
            } catch (Exception e) {
                MessageUtils.log(MessageUtils.LogLevel.WARNING, ChatColor.RED + "There was an issue creating " + name, prefix);
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * Saves the specified file whilst the server is running.
     *
     * @param   name        The name of the file you wish to save.
     * @throws  IOException Throws if the file can not be found.
     */
    public void saveFile(String name) throws IOException {
        /*
        For a more intricate save system, such as save based off of data from the server, that will
        require your own method.
         */

        this.getConfig(name).save(this.getFile(name));
    }

    /**
     * Returns the list of active configuration files.
     *
     * @return The list of active configuration files.
     */
    public HashMap<String, File> getFileList() {
        return fileList;
    }

}
