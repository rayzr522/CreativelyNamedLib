package me.rayzr522.cnl.config.impl;

import me.rayzr522.cnl.config.SerializationHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Objects;
import java.util.UUID;

public class OfflinePlayerHandler implements SerializationHandler<OfflinePlayer> {
    @Override
    public Object serialize(OfflinePlayer value) {
        return null;
    }

    @Override
    public OfflinePlayer deserialize(Object value) {
        UUID id = UUID.fromString(Objects.toString(value));

        return Bukkit.getOfflinePlayer(id);
    }
}
