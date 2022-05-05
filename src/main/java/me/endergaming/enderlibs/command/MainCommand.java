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
import java.util.stream.Collectors;

import static me.endergaming.enderlibs.file.Responses.INVALID_PERMISSION;
import static me.endergaming.enderlibs.file.Responses.NON_PLAYER;


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
        this.permission = String.format("%s.command.%s", plugin.getName().toLowerCase(), command);
        this.playerOnly = playerOnly;

        Permission bukkitPerm = new Permission(this.permission);
        bukkitPerm.setDefault(PermissionDefault.OP);
        Bukkit.getPluginManager().addPermission(bukkitPerm);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Do permission check
        if (!sender.hasPermission(this.permission)) {
            MessageUtils.send(sender, INVALID_PERMISSION);
            return true;
        }
        // Player Check
        if (this.playerOnly) {
            if (!(sender instanceof Player)) {
                MessageUtils.send(sender, NON_PLAYER);
                return true;
            }
        }
        // Argument Check
        if (this.hasCommandArgs) {
            if (args.length == 0 || !this.subCommandMap.containsKey(args[0])) {
                MessageUtils.send(sender, this);
            } else {
                this.subCommandMap.get(args[0]).run(sender, cmd, label, args);
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

    public MainCommand addSubCommand(SubCommand subCommand) {
        subCommand.setPermission(String.format("%s.command.%s.%s", this.plugin.getName().toLowerCase(), this.command, subCommand.command));
        this.subCommandMap.put(subCommand.command, subCommand);
        return this;
    }

    public Map<String, SubCommand> getSubCommandMap() {
        return this.subCommandMap;
    }

    public String getName() {
        return super.command;
    }

    /**
     * This will only be shown to the player when using CommandArguments,
     * for anything else you would have to handle those arguments in your {@link SubCommand#run} function
     *
     * @param usage string message supports color codes
     * @return
     */
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return this.subCommandMap.keySet().stream()
                    .map(String::toLowerCase)
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
