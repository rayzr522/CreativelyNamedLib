/**
 * 
 */
package com.rayzr522.creativelynamedlib.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.configuration.ConfigurationSection;

import com.rayzr522.creativelynamedlib.utils.text.TextUtils;

/**
 * A
 * 
 * @author Rayzr
 */
public class Messages {
    private Map<String, String> messages = new HashMap<>();
    private String prefix = "";

    /**
     * @param config The config to load the messages from
     */
    public void load(ConfigurationSection config) {
        Objects.requireNonNull(config, "config cannot be null!");
        messages.clear();

        for (String key : config.getKeys(true)) {
            if (key.equalsIgnoreCase("prefix")) {
                prefix = TextUtils.colorize(config.getString(key));
                continue;
            }
            messages.put(key, config.getString(key));
        }
    }

    /**
     * @return The raw messages map
     */
    public Map<String, String> getMessages() {
        return messages;
    }

    /**
     * @return The prefix of all messages
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Translates a message from the config
     * 
     * @param key The key of the message
     * @param objects The objects to use in the message
     * @return The translated message
     */
    public String tr(String key, Object... objects) {
        return prefix + trRaw(key, objects);
    }

    /**
     * Returns a raw message from the config (basically just without the prefix)
     * 
     * @param key The key of the message
     * @param objects The objects to use in the message
     * @return The raw message
     */
    public String trRaw(String key, Object... objects) {
        String message = messages.getOrDefault(key, key);
        for (int i = 0; i < objects.length; i++) {
            message = message.replaceAll(String.format("\\{%d\\}", i), objects[i].toString());
        }
        return TextUtils.colorize(message);
    }

}
