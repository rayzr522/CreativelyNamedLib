package me.rayzr522.cnl.config.impl;

import me.rayzr522.cnl.config.SerializationHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Objects;
import java.util.UUID;

public class WorldHandler implements SerializationHandler<World> {
    @Override
    public Object serialize(World value) {
        return value.getUID().toString();
    }

    @Override
    public World deserialize(Object value) {
        UUID id = UUID.fromString(Objects.toString(value));

        return Bukkit.getWorld(id);
    }
}
