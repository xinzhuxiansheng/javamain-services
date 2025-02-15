package kiwi.core.storage;

import java.util.Optional;

public interface KeyValueStore<K, V> extends AutoCloseable {
    void put(K key, V value);

    void put(K key, V value, long ttl);

    Optional<V> get(K key);

    void delete(K key);

    boolean contains(K key);

    int size();

    void purge();
}
