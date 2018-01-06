package me.rayzr522.cnl.config;

import java.util.Map;

public interface SerializationHandler<T> {
    /**
     * Serializes an object into raw data.
     *
     * @param object The object to serialize.
     * @return The serialized data.
     */
    Map<String, Object> serialize(T object);

    /**
     * Deserializes raw data into an object.
     *
     * @param data The data to deserialize.
     * @return The deserialized object.
     */
    T deserialize(Map<String, Object> data);
}
