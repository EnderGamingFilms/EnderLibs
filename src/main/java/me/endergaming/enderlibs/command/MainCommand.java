package me.endergaming.enderlibs.command;

import me.endergaming.enderlibs.text.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.endergaming.enderlibs.file.config.CoreMessages.*;


public abstract class MainCommand extends BaseCommand {
    protected final Map<String, SubCommand> subCommandMap = new HashMap<>();
    protected JavaPlugin plugin;
    protected String permission;
    protected boolean hasCommandArgs;
    protected boolean playerOnly;

    public MainCommand(@NotNull final JavaPlugin plugin, String command) {
        this(plugin, command, false, false, null, Collections.emptyList());
    }

    public MainCommand(@NotNull final JavaPlugin plugin, String command, boolean playerOnly, boolean hasCommandArgs, String usage, List<String> alias) {
        super(command, usage, null, alias);
        this.plugin = plugin;
        this.hasCommandArgs = hasCommandArgs;
        this.permission = String.format("%s.command.%s", plugin.getName(), command);
        this.playerOnly = playerOnly;

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
        // Player Check
        if (playerOnly) {
            if (!(sender instanceof Player))
                MessageUtils.send(sender, NON_PLAYER);
                return true;
        }
        // Argument Check
        if (hasCommandArgs) {
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

    public MainCommand addSubCommand(SubCommand subCommand) {
        this.subCommandMap.put(subCommand.command, subCommand);
        return this;
    }

    public Map<String, SubCommand> getSubCommandMap() {
        return subCommandMap;
    }

    public String getName() {
        return super.command;
    }

    public MainCommand setUsage(String usage) {
        super.usage = usage;
        return this;
    }

    public MainCommand setDescription(String description) {
        super.description = description;
        return this;
    }

    public MainCommand setAlias(String... alias) {
        super.alias = Arrays.asList(alias);
        return this;
    }

    public MainCommand setHasCommandArgs(boolean hasCommandArgs) {
        this.hasCommandArgs = hasCommandArgs;
        return this;
    }

    public MainCommand setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
        return this;
    }
}
