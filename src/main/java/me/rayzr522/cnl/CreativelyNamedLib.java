package me.rayzr522.cnl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CreativelyNamedLib extends JavaPlugin {
    private static final boolean TESTING = Bukkit.getServer() == null;
    private static CreativelyNamedLib instance;

    /**
     * @return The instance of {@link CreativelyNamedLib}. This will be <code>null</code> if the plugin has yet to load or has unloaded.
     */
    public static CreativelyNamedLib getInstance() {
        return instance;
    }

    public static Logger getDebugLogger() {
        return TESTING ? Logger.getLogger("CreativelyNamedLib|Test") : getInstance().getLogger();
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // TODO: Start doing work
        getConfig().getValues(false).forEach((key, value) -> {
            getLogger().info(key + " - " + value.getClass().toString());
        });
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
