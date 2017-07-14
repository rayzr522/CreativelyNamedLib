package me.rayzr522.cnl.config;

public interface SerializationHandler<T extends Object> {
    T deserialize(Object value);

    Object serialize(T value);

    @SuppressWarnings("unchecked")
    default Object _impl_serialize(Object value) {
        return serialize((T) value);
    }
}
