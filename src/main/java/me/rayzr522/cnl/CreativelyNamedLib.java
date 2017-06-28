package me.rayzr522.cnl;

import org.bukkit.plugin.java.JavaPlugin;

public class CreativelyNamedLib extends JavaPlugin {
    private static CreativelyNamedLib instance;

    /**
     * @return The instance of {@link CreativelyNamedLib}. This will be <code>null</code> if the plugin has yet to load or has unloaded.
     */
    public static CreativelyNamedLib getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // TODO: Start doing work
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
