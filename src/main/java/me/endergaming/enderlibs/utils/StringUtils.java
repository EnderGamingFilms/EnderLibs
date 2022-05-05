package me.endergaming.enderlibs.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern UUID_REGEX = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

    public static final String LINE = "&m                                      ";

    private final static int CENTER_PX = 100;
    private final static int MAX_PX = 200;

    /**
     * Fix the given text with making the first letter capitalised and the rest not.
     *
     * @param text the text fixing.
     * @param replaceUnderscore True to replace all _ with a space, false otherwise.
     * @return The new fixed text.
     */
    public static String fix(String text, boolean replaceUnderscore) {
        Validate.notNull(text, "Text cannot be null.");

        if (text.isEmpty()) {
            return text;
        }

        if (text.length() == 1) {
            return text.toUpperCase();
        }

        if (replaceUnderscore) {
            text = text.replace("_", " ");
        }

        StringBuilder builder = new StringBuilder();

        for (String split : text.split(" ")) {
            if (split.isEmpty()) {
                builder.append(" ");
                continue;
            }

            if (split.length() == 1) {
                builder.append(split.toUpperCase()).append(" ");
                continue;
            }

            builder.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase()).append(" ");
        }

        return builder.toString().trim();
    }

    /**
     * Turn the given list into a string version displaying whats in the list.
     *
     * @param list The list to convert.
     * @param color The color of each entry in the list.
     * @param separatorColor The color of the separators between each entry, either a comma or a &.
     * @param <T> The type of the list.
     * @return The string of the list entries.
     */
    public static <T> String listToString(Collection<T> list, ChatColor color, ChatColor separatorColor) {
        return StringUtils.listToString(list, t -> color.toString() + t.toString(), separatorColor);
    }

    /**
     * Turn the given list into a string version displaying whats in the list.
     *
     * @param list The list to convert.
     * @param onAppend What to do for each list entry when turning it to a string.
     * @param separatorColor The color of the separators between each entry, either a comma or a &.
     * @param <T> The type of the list.
     * @return The string of the list entries.
     */
    public static <T> String listToString(Collection<T> list, Function<T, String> onAppend, ChatColor separatorColor) {
        return StringUtils.listToString(list, onAppend, separatorColor.toString());
    }


    /**
     * Turn the given list into a string version displaying whats in the list.
     *
     * @param list The list to convert.
     * @param prefix The prefix before each entry in the list.
     * @param separatorPrefix The prefix of the separator of each entry.
     * @param <T> The type of the list.
     * @return The string of the list entries.
     */
    public static <T> String listToString(Collection<T> list, String prefix, String separatorPrefix) {
        return StringUtils.listToString(list, t -> prefix + t.toString(), separatorPrefix);
    }

    /**
     * Turn the given list into a string version displaying whats in the list.
     *
     * @param list The list to convert.
     * @param onAppend What to do for each list entry when turning it to a string.
     * @param separatorPrefix The prefix of the separator of each entry.
     * @param <T> The type of the list.
     * @return The string of the list entries.
     */
    public static <T> String listToString(Collection<T> list, Function<T, String> onAppend, String separatorPrefix) {
        Validate.notNull(list, "String list cannot be null.");

        if (separatorPrefix == null) {
            separatorPrefix = "";
        }

        StringBuilder toReturn = new StringBuilder();
        int i = 1;

        for (T entry : list) {
            if (toReturn.length() > 0) {
                if (i == list.size()) {
                    toReturn.append(separatorPrefix).append(" & ");
                } else {
                    toReturn.append(separatorPrefix).append(", ");
                }
            }

            toReturn.append(onAppend.apply(entry));
            i++;
        }

        return toReturn.toString().trim();
    }

    /**
     * Get the given name with an apostrophe at the end with the correct grammar.
     *
     * @param name The name to use.
     * @return The name with the apostrophe
     */
    public static String getNameWithApostrophe(String name) {
        String toCheck = ChatColor.stripColor(name);

        if (toCheck.toLowerCase().endsWith("s") || toCheck.toLowerCase().endsWith("ch") || toCheck.toLowerCase().endsWith("z")) {
            return name + "'";
        } else {
            return name + "'s";
        }
    }

    /**
     * Get the ordinal indicator suffix for the given number
     *
     * @param number The number to use.
     * @return The ordinal indicator.
     */
    public static String getOrdinalIndicator(int number) {
        String numberStr = Integer.toString(number);

        if (!numberStr.endsWith("11") && !numberStr.endsWith("12") && !numberStr.endsWith("13")) {
            if (numberStr.endsWith("1")) {
                return number + "st";
            }

            if (numberStr.endsWith("2")) {
                return number + "nd";
            }

            if (numberStr.endsWith("3")) {
                return number + "rd";
            }
        }

        return number + "th";
    }

    /**
     * Send the given message centered on their chat for the given player.
     *
     * @param message The message to center and send.
     * @author SirSpoodles
     */
    public static String getCenteredMessage(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        int messagePxSize = 0;

        boolean previousCode = false;
        boolean isBold = false;

        int charIndex = 0;
        int lastSpaceIndex = 0;

        String toSendAfter = null;
        String recentColorCode = "";

        for (char c : message.toCharArray()) {
            if (c == '§'){
                previousCode = true;
                continue;
            } else if (previousCode) {
                previousCode = false;
                recentColorCode = "§" + c;

                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else {
                    isBold = false;
                }
            } else if (c == ' ') {
                lastSpaceIndex = charIndex;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }

            if (messagePxSize >= MAX_PX) {
                toSendAfter = recentColorCode + message.substring(lastSpaceIndex + 1);
                message = message.substring(0, lastSpaceIndex + 1);
                break;
            }

            charIndex++;
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;

        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();

        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        String fullMsg = sb + message;

        if (toSendAfter != null) {
            fullMsg += "\n" + ChatColor.getLastColors(fullMsg) + getCenteredMessage(toSendAfter);
        }

        return fullMsg;
    }

    /**
     * Default font information enum class.
     */
    public enum DefaultFontInfo {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PARENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public char getCharacter(){
            return this.character;
        }

        public int getLength(){
            return this.length;
        }

        public int getBoldLength(){
            if (this == DefaultFontInfo.SPACE) {
                return this.getLength();
            }

            return this.length + 1;
        }

        public static DefaultFontInfo getDefaultFontInfo(char c) {
            for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
                if (dFI.getCharacter() == c) {
                    return dFI;
                }
            }

            return DefaultFontInfo.DEFAULT;
        }
    }
}
