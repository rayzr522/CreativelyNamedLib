package me.rayzr522.cnl

import org.bukkit.plugin.java.JavaPlugin

/**
 * The plugin class for CreativelyNamedLib.
 *
 * @author Rayzr
 */
class CreativelyNamedLib : JavaPlugin() {
    companion object {
        lateinit var instance: CreativelyNamedLib
            private set
    }

    override fun onEnable() {
        instance = this
    }
}