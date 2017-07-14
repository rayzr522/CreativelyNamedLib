package me.rayzr522.cnl.config.impl;

import me.rayzr522.cnl.config.SerializationHandler;

import java.util.Objects;
import java.util.UUID;

public class UUIDHandler implements SerializationHandler<UUID> {
    @Override
    public UUID deserialize(Object value) {
        return UUID.fromString(Objects.toString(value));
    }

    @Override
    public Object serialize(UUID value) {
        return value.toString();
    }
}
