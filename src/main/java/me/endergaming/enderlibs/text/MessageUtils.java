package me.endergaming.enderlibs.text;

import me.endergaming.enderlibs.command.BaseCommand;
import me.endergaming.enderlibs.file.Responses;
import me.endergaming.enderlibs.misc.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class MessageUtils {
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\{#([A-Fa-f0-9]{6})}");

    /**
     * Returns the message with added prefix and color.
     *
     * @param   message The message you want to send to the player.
     * @param   prefix  Some prefix used for formatting
     * @return          The finalized message.
     */
    public static String format(String message, String prefix) {
        message = prefix + message;

        return colorize(message);
    }

    /**
     * Returns the message colorized via color code. For hex colors use <code>{#123456}</code> instead of just <code>&c</code>
     *
     * @param   message The message you want to send to the player, containing a color code.
     * @return          The resulting message implemented with color.
     */
    public static String colorize(String message) {
        try {
            if (Integer.parseInt(ServerUtils.getServerVersion().split("\\.")[1]) >= 16) {
                message = translateHexColorCodes(message);
            }
        } finally {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        return message;
    }

    public static String translateHexColorCodes(String message) {
        return translateHexColorCodes(DEFAULT_PATTERN, message);
    }

    public static String translateHexColorCodes(String startTag, String endTag, String message) {
        final Pattern pattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);

        return translateHexColorCodes(pattern, message);
    }

    public static String translateHexColorCodes(final Pattern hexPattern, String message) {
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }

        return matcher.appendTail(buffer).toString();
    }

    public static void send(CommandSender sender, String message) {
        message = colorize(message);
        sender.sendMessage(message);
    }

    public static void send(Player player, String message) {
        message = colorize(message);
        player.sendMessage(message);
    }


    public static void send(CommandSender sender, BaseCommand command) {
        String message = "";

        if (!command.getDescription().equals("")) {
            message += command.getDescription().concat("\n");
        }

        if (!command.getUsage().equals("")) {
            message += command.getUsage();
        } else {
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

    public static void send(CommandSender sender, Responses.ErrorMessage errorMessage) {
        send(sender, errorMessage.toString());
    }

    /**
     * INFO = Logger.info<br>
     * WARNING = Logger.warning<br>
     * SEVERE = Logger.severe
     */
    public enum LogLevel {
        INFO, WARNING, SEVERE
    }

    public static void log(final LogLevel logLevel, String msg) {
        log(logLevel, msg, "[" + ServerUtils.getCallingPlugin().getName() + "]");
    }

    /**
     * This method will log a message to the console.
     *
     * @param   logLevel    The LogLevel enum determining the severity of the logged message.
     * @param   msg         The message that should be sent to the console.
     */
    public static void log(final LogLevel logLevel, String msg, String prefix) {
        msg = colorize(msg);

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
                throw new IllegalStateException("Undefined LogLevel: " + logLevel);
        }
    }
}
