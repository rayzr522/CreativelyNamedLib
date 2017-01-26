/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

/**
 * Various text-related utilities
 * 
 * @author Rayzr
 */
public class TextUtils {

    /**
     * Translates ampersand (<code>&</code>) color/formatting codes to internal Minecraft color/formatting codes that use the section sign (<code>\u00A7</code>)
     * 
     * @param input The input text to convert
     * @return The converted text
     */
    public static String colorize(String input) {
        return input == null ? null : ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Applies {@link #colorize(String)} to all elements in a {@link List}
     * 
     * @param input The input list of text to convert
     * @return The converted list
     */
    public static List<String> colorize(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::colorize).collect(Collectors.toList());
    }

    /**
     * Removes all color/formatting codes from the given input
     * 
     * @param input The text to strip formatting from
     * @return The simplified text
     */
    public static String stripColor(String input) {
        return input == null ? null : ChatColor.stripColor(input);
    }

    /**
     * Applies {@link #stripColor(String)} to all elements in a {@link List}
     * 
     * @param input The input list of text to strip formatting from
     * @return The simplified list
     */
    public static List<String> stripColor(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::stripColor).collect(Collectors.toList());
    }

    /**
     * Translates internal Minecraft color/formatting codes that use the section sign (<code>\u00A7</code>) to ampersand (<code>&</code>) color/formatting codes
     * 
     * @param input The input text to convert
     * @return The converted text
     */
    public static String uncolorize(String input) {
        return input == null ? null : input.replace(ChatColor.COLOR_CHAR, '&');
    }

    /**
     * Applies {@link #uncolorize(String)} to all elements in a {@link List}
     * 
     * @param input The input list of text to convert
     * @return The converted list
     */
    public static List<String> uncolorize(List<String> input) {
        return input == null ? null : input.stream().map(TextUtils::uncolorize).collect(Collectors.toList());
    }

}
