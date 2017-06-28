package me.rayzr522.cnl.utils

import org.bukkit.ChatColor

/**
 * @return This [String] with ampersand color codes translated to use [ChatColor.COLOR_CHAR].
 */
fun String.colorize() = ChatColor.translateAlternateColorCodes('&', this)

/**
 * @return This [String] with [ChatColor.COLOR_CHAR] color codes translated to use ampersands.
 */
fun String.uncolorize() = this.replace(ChatColor.COLOR_CHAR, '&')

/**
 * @return This [String] with all [ChatColor.COLOR_CHAR] color codes removed.
 */
fun String.stripColor() = ChatColor.stripColor(this)


/**
 * @return This list of [String]s with ampersand color codes translated to use [ChatColor.COLOR_CHAR].
 */
fun List<String?>.colorize() = this.map { it?.colorize() }

/**
 * @return This list of [String]s with [ChatColor.COLOR_CHAR] color codes translated to use ampersands.
 */
fun List<String?>.uncolorize() = this.map { it?.uncolorize() }

/**
 * @return This list of [String]s with all [ChatColor.COLOR_CHAR] color codes removed.
 */
fun List<String?>.stripColor() = this.map { it?.stripColor() }