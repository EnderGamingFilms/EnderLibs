package me.endergaming.enderlibs.text;

import me.endergaming.enderlibs.EnderLibs;
import me.endergaming.enderlibs.command.BaseCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Pattern hexPattern = Pattern.compile("(\\{)(?<!\\\\)(#[a-fA-F0-9]{6})(})");
    private static String logPrefix = EnderLibs.getInstance().getConfig().getString("MESSAGES.PREFIX.log");

    /**
     * Returns the message with added prefix and color.
     *
     * @param   message The message you want to send to the player.
     * @return          The finalized message.
     */
    public static String format(String message) {
        try {
            message = withPrefix(message);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return colorize(message);
    }

    public static String withPrefix(String message) throws FileNotFoundException {
        message = logPrefix + message;
        return message;
    }

    /**
     * Returns the message colorized via color code and/or hex code.
     *
     * @param   message The message you want to send to the player, containing a color code and/or hex code.
     * @return          The resulting message implemented with color.
     */
    public static String colorize(String message) {
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher matcher = hexPattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, "" + ChatColor.of(color));
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void send(Player player, String message) {
        message = colorize(message);
        player.sendMessage(message);
    }

    public static void send(CommandSender sender, String message) {
        message = colorize(message);
        sender.sendMessage(message);
    }

    public static void send(CommandSender sender, BaseCommand command) {
        String message = "";

        if (command.getDescription() != null) {
            message += command.getDescription().concat("\n");
        }

        if (command.getUsage() != null) {
            message += command.getUsage();
        } else {
            // Default message for commands that do not have a default usage message
//            message = HELP;
            message = "&cSorry but we can't help you here.";
        }

        message = colorize(message);
        sender.sendMessage(message);
    }

    public static void send(Player player, BaseComponent message) {
        player.spigot().sendMessage(message);
    }

    public static void send(CommandSender sender, BaseComponent message) {
        sender.spigot().sendMessage(message);
    }

    /**
     * INFO = Logger.info<br>
     * WARNING = Logger.warning<br>
     * SEVERE = Logger.severe
     */
    public enum LogLevel {
        INFO, WARNING, SEVERE
    }

    /**
     * This method will log a message to the console.
     *
     * @param   logLevel    The LogLevel enum determining the severity of the logged message.
     * @param   msg         The message that should be sent to the console.
     */
    public static void log(final LogLevel logLevel, String msg) {
        msg = format(logPrefix + msg);
        Logger logger = Bukkit.getLogger();
        switch (logLevel) {
            case INFO:
                logger.info(msg);
                break;
            case WARNING:
                logger.warning(msg);
                break;
            case SEVERE:
                logger.severe(msg);
                break;
            default:
                throw new IllegalStateException("Undefined LogLevel: " + logLevel.toString());
        }
    }
}
