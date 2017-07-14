package me.rayzr522.cnl.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.Reader;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BetterConfig {
    private ConfigurationSection base;

    private BetterConfig(ConfigurationSection base) {
        Objects.requireNonNull(base, "base cannot be null!");

        this.base = base;
    }

    public static BetterConfig from(ConfigurationSection section) {
        Objects.requireNonNull(section, "section cannot be null!");

        return new BetterConfig(section);
    }

    public static BetterConfig load(File file) {
        Objects.requireNonNull(file, "file cannot be null!");

        return from(YamlConfiguration.loadConfiguration(file));
    }

    /**
     * Loads a {@link BetterConfig} from a {@link Reader}.
     *
     * @param reader The reader to load the config from.
     * @return The loaded {@link BetterConfig}.
     */
    public static BetterConfig load(Reader reader) {
        Objects.requireNonNull(reader, "reader cannot be null!");

        return from(YamlConfiguration.loadConfiguration(reader));
    }

    /**
     * @return The base {@link ConfigurationSection}.
     */
    public ConfigurationSection getBase() {
        return base;
    }

    /**
     * @param path The path to the section.
     * @return The section at the given path.
     */
    public BetterConfig getSection(String path) {
        Objects.requireNonNull(path, "path cannot be null!");

        return new BetterConfig(base.getConfigurationSection(path));
    }

    /**
     * Returns a set of keys that this configuration section contains.
     *
     * @param deep Whether or not to include deep keys.
     * @return The set of keys.
     */
    public Set<String> getKeys(boolean deep) {
        return base.getKeys(deep);
    }

    /**
     * Returns a map of values that this configuration section contains.
     *
     * @param deep Whether or not to include deep values.
     * @return The map of values.
     */
    public Map<String, Object> getValues(boolean deep) {
        return base.getValues(deep);
    }

    /**
     * @return The path to this config section.
     */
    public String getCurrentPath() {
        return base.getCurrentPath();
    }

    /**
     * @return The name of this config section.
     */
    public String getName() {
        return base.getName();
    }

    /**
     * @return The parent config.
     */
    public BetterConfig getParent() {
        return base.getParent() == null ? null : new BetterConfig(base.getParent());
    }

    /**
     * Returns an object that implements {@link SerializableData}.
     *
     * @param path      The path of the data.
     * @param dataClass The data class that implements {@link SerializableData}.
     * @param <T>       The type of the class.
     * @return The data, or <code>null</code> if something went wrong.
     */
    public <T extends SerializableData> T getData(String path, Class<T> dataClass) {
        Objects.requireNonNull(path, "path cannot be null!");
        Objects.requireNonNull(dataClass, "dataClass cannot be null!");

        return Serializer.deserialize(base.getConfigurationSection(path), dataClass);
    }

    /**
     * Sets a value under a certain path.
     *
     * @param path  The path.
     * @param value The value to set.
     */
    public void set(String path, Object value) {
        Objects.requireNonNull(path, "path cannot be null!");
        
        if (value != null && value instanceof SerializableData) {
            Serializer.saveToConfig((SerializableData) value, base, path);
        } else {
            base.set(path, value);
        }
    }
}
