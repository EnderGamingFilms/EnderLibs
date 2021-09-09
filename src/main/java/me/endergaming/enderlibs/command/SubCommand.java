package me.endergaming.enderlibs.command;

import me.endergaming.enderlibs.text.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static me.endergaming.enderlibs.file.Responses.INVALID_PERMISSION;

public abstract class SubCommand extends BaseCommand {
    protected Map<String, SubCommand> subCommandMap = new HashMap<>();
    protected JavaPlugin plugin;
    protected String permission;
    protected boolean hasCommandArgs;

    public SubCommand(@NotNull final JavaPlugin plugin, String command) {
        this(plugin, command, false, null);
    }

    public SubCommand(@NotNull final JavaPlugin plugin, String command, boolean hasCommandArgs, String usage) {
        super(command, usage);
        this.plugin = plugin;
        this.hasCommandArgs = hasCommandArgs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Do permission check
        if (permission != null && !sender.hasPermission(permission)) {
            MessageUtils.send(sender, INVALID_PERMISSION);
            return true;
        }
        // Argument Check
        if (hasCommandArgs) {
            if (args.length == 0 || !subCommandMap.containsKey(args[0])) {
                MessageUtils.send(sender, this);
            } else {
                subCommandMap.get(args[0]).run(sender, cmd, label, args);
            }
            return true;
        }
        // Run() w/o Args
        this.run(sender, cmd, label, args);
        return true;
    }

    /**
     * Main command functionality. You must overload this in anything that extends this class
     *
     * @param sender Some ConsoleSender
     * @param cmd Command Object
     * @param label Command Name
     * @param args Command Arugments
     */
    public abstract void run(CommandSender sender, Command cmd, String label, String[] args);

    /** Sub-commands inside of sub-commands poggers **/
    public SubCommand addSubCommand(SubCommand subCommand) {
        this.subCommandMap.put(subCommand.command, subCommand);
        return this;
    }

    public Map<String, SubCommand> getSubCommandMap() {
        return subCommandMap;
    }

    public String getName() {
        return command;
    }

    /**
     * This will only be shown to the player when using CommandArguments,
     * for anything else you would have to handle those arguments in your {@link SubCommand#run} function
     *
     * @param usage string message supports color codes
     * @return
     */
    public SubCommand setUsage(String usage) {
        super.usage = usage;
        return this;
    }

    public SubCommand setDescription(String description) {
        super.description = description;
        return this;
    }

    public SubCommand setHasCommandArgs(boolean hasCommandArgs) {
        this.hasCommandArgs = hasCommandArgs;
        return this;
    }

    // Make sure that this doesn't accidentally get registered lol
    @Override
    public void register() {
        MessageUtils.log(MessageUtils.LogLevel.SEVERE, "Please don't try to manually register subcommands.");
    }

    public void setPermission(String permission) {
        this.permission = permission;

        Permission bukkitPerm = new Permission(this.permission);
        bukkitPerm.setDefault(PermissionDefault.OP);
        Bukkit.getPluginManager().addPermission(bukkitPerm);
    }
}
