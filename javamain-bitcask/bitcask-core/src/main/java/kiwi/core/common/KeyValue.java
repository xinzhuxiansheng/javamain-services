package kiwi.core.common;

public record KeyValue<K, V>(K key, V value) {
    public static <K, V> KeyValue<K, V> of(K key, V value) {
        return new KeyValue<>(key, value);
    }
}
