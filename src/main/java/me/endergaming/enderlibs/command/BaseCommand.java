package me.endergaming.enderlibs.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabExecutor {
    protected final String command;
    protected final String permMessage;
    protected String description;
    protected List<String> alias;
    protected String usage;
    protected boolean registered;

    protected static CommandMap cmap;

    /**
     * Just a command.
     *
     * @param command the command.
     * @since 1.0
     */
    public BaseCommand(String command) {
        this(command, null, null, null, null);
    }

    /**
     * Command and usage.
     *
     * @param command the command.
     * @param usage   The command usage
     * @since 1.0
     */
    public BaseCommand(String command, String usage) {
        this(command, usage, null, null, null);
    }

    /**
     * Command, usage & description!
     *
     * @param command     the command.
     * @param usage       The command usage
     * @param description About the command.
     * @since 1.0
     */
    public BaseCommand(String command, String usage, String description) {
        this(command, usage, description, null, null);
    }

    /**
     * Command, usage, description and permissions message.
     *
     * @param command           the command.
     * @param usage             The command usage
     * @param description       About the command.
     * @param permissionMessage The permission message!
     * @since 1.0
     */
    public BaseCommand(String command, String usage, String description, String permissionMessage) {
        this(command, usage, description, permissionMessage, null);
    }

    /**
     * Command, usage, description and aliases.
     *
     * @param command     the command.
     * @param usage       The command usage
     * @param description About the command.
     * @param aliases     Aliases.
     */
    public BaseCommand(String command, String usage, String description, List<String> aliases) {
        this(command, usage, description, null, aliases);
    }

    /**
     * Command, usage, description, permissionsMessage and Aliases.
     *
     * @param command           the command.
     * @param usage             The command usage
     * @param description       About the command.
     * @param permissionMessage The permission message!
     * @param aliases           aliases.
     * @since 1.0
     */
    public BaseCommand(String command, String usage, String description, String permissionMessage, List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
        this.registered = false;
    }

    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null) cmd.setAliases(this.alias);
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
        if (this.permMessage != null) cmd.setPermissionMessage(this.permMessage);
        this.getCommandMap().register("", cmd);
        cmd.setExecutor(this);
        this.registered = true;
    }

    public String getUsage() {
        return this.usage != null ? this.usage.replace("<command>", this.command) : "";
    }

    public String getDescription() {
        return this.description != null ? this.description.replace("<command>", this.command) : "";
    }

    final CommandMap getCommandMap() {
        if (cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return this.getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return cmap;
        }
        return this.getCommandMap();
    }

    private final class ReflectCommand extends Command {
        private BaseCommand exe = null;

        protected ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(BaseCommand exe) {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            return this.exe != null && this.exe.onCommand(sender, this, commandLabel, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            if (this.exe != null) {
                return this.exe.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) { return null; }

}