package me.endergaming.enderlibs.text;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;

public class ColorUtils {
    /**
     * Convert color codes and hex codes to RGB
     * @param value e.g. "#FFFFFF" or "&c"
     * @return Bukkit.Color
     */
    public static Color getColorFromCode(String value) {
        if (value.startsWith("#")) {
            return hex2Rgb(value);
        } else {
            return minecraft2Rgb(value);
        }
    }

    /**
     * Convert string to ChatColor
     * @param value e.g. "#FFFFFF" or "&c"
     * @return ChatColor
     */
    public static ChatColor getChatColorFromCode(String value) {
        if (value.matches("&([A-z0-9]){1}")) {
//            return ChatColor.valueOf(value);
            if (value.length() <= 1) return ChatColor.GRAY;
            return ChatColor.getByChar(value.charAt(1));
        } else if (value.matches("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")) {
            return ChatColor.of(value);
        }
        return ChatColor.GRAY;
    }

    /**
     * @param colorStr e.g. "#FFFFFF"
     * @return
     */
    private static Color hex2Rgb(String colorStr) {
        return Color.fromBGR(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    /**
     * @param colorStr e.g. "&c"
     * @return
     */
    private static Color minecraft2Rgb(String colorStr) {
        switch (colorStr) {
            case "&0":
                return Color.fromRGB(0, 0, 0);
            case "&1":
                return Color.fromRGB(0, 0, 170);
            case "&2":
                return Color.fromRGB(0, 170, 0);
            case "&3":
                return Color.fromRGB(0, 170, 170);
            case "&4":
                return Color.fromRGB(170, 0, 0);
            case "&5":
                return Color.fromRGB(170, 0, 170);
            case "&6":
                return Color.fromRGB(255, 170, 0);
            case "&7":
                return Color.fromRGB(170, 170, 170);
            case "&8":
                return Color.fromRGB(85, 85, 85);
            case "&9":
                return Color.fromRGB(85, 85, 255);
            case "&a":
                return Color.fromRGB(85, 255, 85);
            case "&b":
                return Color.fromRGB(85, 255, 255);
            case "&c":
                return Color.fromRGB(255, 85, 85);
            case "&d":
                return Color.fromRGB(255, 85, 255);
            case "&e":
                return Color.fromRGB(255, 255, 85);
            default:
                return Color.fromRGB(255, 255, 255);
        }
    }

}