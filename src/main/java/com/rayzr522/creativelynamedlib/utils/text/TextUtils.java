/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils.text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

/**
 * Various text-related utilities
 * 
 * @author Rayzr
 */
public class TextUtils {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###.##");

    /**
     * Translates ampersand (<code>&</code>) color/formatting codes to internal Minecraft color/formatting codes that use the section sign (<code>\u00A7</code>)
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The input text to convert
     * @return The converted text
     */
    public static String colorize(String input) {
        return input == null ? null : ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Applies {@link #colorize(String)} to all elements in a {@link List}
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The input list of text to convert
     * @return The converted list
     */
    public static List<String> colorize(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::colorize).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Removes all color/formatting codes from the given input
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The text to strip formatting from
     * @return The simplified text
     */
    public static String stripColor(String input) {
        return input == null ? null : ChatColor.stripColor(input);
    }

    /**
     * Applies {@link #stripColor(String)} to all elements in a {@link List}
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The input list of text to strip formatting from
     * @return The simplified list
     */
    public static List<String> stripColor(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::stripColor).collect(Collectors.toList());
    }

    /**
     * Translates internal Minecraft color/formatting codes that use the section sign (<code>\u00A7</code>) to ampersand (<code>&</code>) color/formatting codes
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The input text to convert
     * @return The converted text
     */
    public static String uncolorize(String input) {
        return input == null ? null : input.replace(ChatColor.COLOR_CHAR, '&');
    }

    /**
     * Applies {@link #uncolorize(String)} to all elements in a {@link List}
     * <br>
     * <br>
     * This method <code>null</code>-forwards, so if the input is <code>null</code>, the method will return <code>null</code>.
     * 
     * @param input The input list of text to convert
     * @return The converted list
     */
    public static List<String> uncolorize(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::uncolorize).collect(Collectors.toList());
    }

    /**
     * @param number The number to format
     * @return The formatted number
     * @see java.text.NumberFormat#format(double)
     */
    public static String format(double number) {
        return FORMATTER.format(number);
    }

    /**
     * @param number The number to format
     * @return The formatted number
     * @see java.text.NumberFormat#format(long)
     */
    public static String format(long number) {
        return FORMATTER.format(number);
    }

    /**
     * @param input the input to convert to camel case
     * @return
     */
    public static String camelCase(String input) {
        StringBuilder builder = new StringBuilder();
        String[] split = input.toLowerCase().split(" ");
        for (String str : split) {
            if (str.length() < 2) {
                builder.append(str.toUpperCase());
            } else {
                builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
            }
        }
        return builder.toString();
    }

}
