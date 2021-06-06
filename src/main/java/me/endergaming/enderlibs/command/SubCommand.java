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

import static me.endergaming.enderlibs.file.config.CoreMessages.INVALID_ARGUMENT;
import static me.endergaming.enderlibs.file.config.CoreMessages.INVALID_PERMISSION;

public abstract class SubCommand extends BaseCommand {
    protected Map<String, SubCommand> subCommandMap = new HashMap<>();
    protected JavaPlugin plugin;
    protected String permission;
    protected boolean hasArgs;

    public SubCommand(@NotNull final JavaPlugin plugin, String command) {
        this(plugin, command, false, null);
    }

    public SubCommand(@NotNull final JavaPlugin plugin, String command, boolean hasArgs, String usage) {
        super(command, usage);
        this.plugin = plugin;
        this.hasArgs = hasArgs;
        this.permission = String.format("%s.command.%s", plugin.getName(), command);

        Permission bukkitPerm = new Permission(this.permission);
        bukkitPerm.setDefault(PermissionDefault.OP);
        Bukkit.getPluginManager().addPermission(bukkitPerm);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Do permission check
        if (!sender.hasPermission(permission)) {
            MessageUtils.send(sender, INVALID_PERMISSION);
            return true;
        }
        // Argument Check
        if (hasArgs) {
            if (args.length == 0) {
                MessageUtils.send(sender, this);
            } else if (subCommandMap.containsKey(args[0])) {
                subCommandMap.get(args[0]).run(sender, cmd, label, args);
            } else {
                MessageUtils.send(sender, INVALID_ARGUMENT);
            }
            return true;
        }
        // Run() w/o Args
        this.run(sender, cmd, label, args);
        return true;
    }

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

    public SubCommand setUsage(String usage) {
        super.usage = usage;
        return this;
    }

    public SubCommand setDescription(String description) {
        super.description = description;
        return this;
    }

    public SubCommand setHasArgs(boolean hasArgs) {
        this.hasArgs = hasArgs;
        return this;
    }

    // Make sure that this doesn't accidentally get registered lol
    @Override
    public void register() {
        MessageUtils.log(MessageUtils.LogLevel.SEVERE, "Please don't try to manually register subcommands.");
    }
}
