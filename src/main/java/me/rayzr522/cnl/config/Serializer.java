package me.rayzr522.cnl.config;

import com.google.common.collect.ImmutableMap;
import me.rayzr522.cnl.CreativelyNamedLib;
import me.rayzr522.cnl.config.impl.OfflinePlayerHandler;
import me.rayzr522.cnl.config.impl.UUIDHandler;
import me.rayzr522.cnl.config.impl.WorldHandler;
import me.rayzr522.cnl.utils.TextUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serializer {
    private static final Map<Class<?>, SerializationHandler<?>> serializationHandlers = new HashMap<>();
    private static final ImmutableMap<Object, Object> primitiveMaps = ImmutableMap.builder()
            .put(int.class, Integer.class)
            .put(double.class, Double.class)
            .put(float.class, Float.class)
            .put(long.class, Long.class)
            .put(byte.class, Byte.class)
            .put(short.class, Short.class)
            .put(boolean.class, Boolean.class)
            .build();

    static {
        Serializer.registerHandler(UUID.class, new UUIDHandler());
        Serializer.registerHandler(World.class, new WorldHandler());
        Serializer.registerHandler(OfflinePlayer.class, new OfflinePlayerHandler());
    }

    private static Class<?> remapPrimitives(Class<?> clazz) {
        return (Class<?>) primitiveMaps.getOrDefault(clazz, clazz);
    }

    /**
     * Registers a handler for dealing with non-standard data (e.g. {@link UUID}s, {@link org.bukkit.World}s, etc).
     *
     * @param clazz                The class to be handled.
     * @param serializationHandler The {@link SerializationHandler} for that class.
     * @param <T>                  The type of the class.
     */
    public static <T> void registerHandler(Class<T> clazz, SerializationHandler<T> serializationHandler) {
        Objects.requireNonNull(clazz, "clazz cannot be null!");
        Objects.requireNonNull(serializationHandler, "serializationHandler cannot be null!");

        if (serializationHandlers.containsKey(clazz)) {
            CreativelyNamedLib.getInstance().getLogger().warning(String.format(
                    "Attempted to register serialization handler for class %s (%s), but one was already present.",
                    clazz.getCanonicalName(),
                    serializationHandler.getClass().getCanonicalName()
            ));
            return;
        }

        serializationHandlers.put(clazz, serializationHandler);
    }

    /**
     * Converts an object to a {@link YamlConfiguration}, ready to save.
     *
     * @param object The object to serialize.
     * @param <T>    The type of the object.
     * @return The serialized config.
     */
    public static <T extends SerializableData> YamlConfiguration toConfig(T object) {
        YamlConfiguration output = new YamlConfiguration();

        serializeToConfig(object, output);

        return output;
    }

    /**
     * Saves an object to a {@link ConfigurationSection} under the given key.
     *
     * @param object The object to serialize.
     * @param root   The root {@link ConfigurationSection} to save to.
     * @param key    The key under which to save the serialized data.
     * @param <T>    The type of the object.
     * @return The child {@link ConfigurationSection} containing the serialized data.
     */
    public static <T extends SerializableData> ConfigurationSection saveToConfig(T object, ConfigurationSection root, String key) {
        Objects.requireNonNull(root, "root cannot be null!");
        Objects.requireNonNull(key, "key cannot be null!");

        ConfigurationSection section = root.createSection(key);

        serializeToConfig(object, section);

        return section;
    }

    private static void serializeToConfig(Object object, ConfigurationSection config) {
        Objects.requireNonNull(config, "config cannot be null!");

        if (object == null) {
            // Nothing to save!
            return;
        }

        Logger logger = CreativelyNamedLib.getDebugLogger();

        Class<?> dataClass = object.getClass();
        if (!SerializableData.class.isAssignableFrom(dataClass)) {
            logger.severe("Failed to serialize object of type " + dataClass.getCanonicalName() + " because it did not implement the SerializableData interface.");
            return;
        }

        for (Field field : dataClass.getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            String key = TextUtils.convertCamelCaseToHyphens(field.getName());

            Class<?> fieldType = remapPrimitives(field.getType());

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object fieldValue;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Failed to get value of field " + field.getName() + " in class " + dataClass.getCanonicalName(), e);
                continue;
            }

            if (fieldValue == null) {
                config.set(key, null);
                continue;
            }

            if (SerializableData.class.isAssignableFrom(fieldType)) {
                ConfigurationSection child = config.createSection(key);

                serializeToConfig(fieldValue, child);
            } else if (serializationHandlers.containsKey(fieldType)) {
                config.set(key, serializationHandlers.get(fieldType)._impl_serialize(fieldValue));
            } else {
                config.set(key, fieldValue);
            }
        }
    }

    /**
     * Deserializes a {@link ConfigurationSection} into a data class.
     *
     * @param config    The {@link ConfigurationSection} to deserialize.
     * @param dataClass The data class to deserialize to.
     * @param <T>       The type of the data class.
     * @return The deserialized data, or <code>null</code> if something went wrong.
     */
    public static <T extends SerializableData> T deserialize(ConfigurationSection config, Class<T> dataClass) {
        Objects.requireNonNull(config, "config cannot be null!");
        Objects.requireNonNull(dataClass, "dataClass cannot be null!");

        return deserialize(config.getValues(false), dataClass);
    }

    @SuppressWarnings("unchecked")
    private static <T extends SerializableData> T deserialize(Map<String, Object> map, Class<T> dataClass) {
        Objects.requireNonNull(map, "map cannot be null!");
        Objects.requireNonNull(dataClass, "dataClass cannot be null!");

        // I'm going to be using this a *lot*, might as well store it in a variable.
        Logger logger = CreativelyNamedLib.getDebugLogger();

        if (!SerializableData.class.isAssignableFrom(dataClass)) {
            logger.severe("Tried to deserialize to class " + dataClass.getCanonicalName() + ", but it does not implement the SerializableData interface.");
            return null;
        }

        Constructor<T> constructor;
        try {
            constructor = dataClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.severe("The class " + dataClass.getCanonicalName() + " is missing a default (empty) constructor.");
            return null;
        }

        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        T instance;
        try {
            instance = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.SEVERE, "Failed to create instance of class " + dataClass.getCanonicalName(), e);
            return null;
        }

        for (Field field : dataClass.getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            String key = TextUtils.convertCamelCaseToHyphens(field.getName());

            Object value = map.get(key);
            if (value == null) {
                continue;
            }

            Class<?> fieldType = remapPrimitives(field.getType());

            Object fieldValue;

            if (SerializableData.class.isAssignableFrom(fieldType) && value instanceof MemorySection) {
                Map<String, Object> values = ((MemorySection) value).getValues(false);

                fieldValue = deserialize(values, (Class<T>) fieldType);
            } else if (serializationHandlers.containsKey(fieldType)) {
                try {
                    fieldValue = serializationHandlers.get(fieldType).deserialize(value);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    continue;
                }
            } else {
                if (!fieldType.isAssignableFrom(value.getClass())) {
                    logger.severe("Failed to assign data of type " + value.getClass().getCanonicalName() + " to field of type " + fieldType.getCanonicalName() + " in class " + dataClass.getCanonicalName());
                    continue;
                }

                fieldValue = value;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                field.set(instance, fieldValue);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Failed to assign value " + fieldValue + " to field " + field.getName() + " in class " + dataClass.getCanonicalName(), e);
            }
        }

        return instance;
    }
}