package epf.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author PC
 *
 */
public interface MapUtil {

	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	static <K,V> Optional<V> get(final Map<K, V> map, final K key){
		Objects.requireNonNull(map, "Map");
		return Optional.ofNullable(map.get(key));
	}
	
	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	static <K, V> Optional<V> remove(final Map<K, V> map, final K key){
		Objects.requireNonNull(map, "Map");
		return Optional.ofNullable(map.remove(key));
	}
	
	/**
	 * @param <K>
	 * @param <V>
	 * @param key
	 * @param value
	 * @return
	 */
	static <K, V> Map<K, V> of(final K key, final V value){
		final Map<K, V> map = new HashMap<>();
		map.put(key, value);
		return map;
	}
	
	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param filter
	 * @return
	 */
	static <K, V> Optional<V> findFirst(final Map<K, V> map, final Predicate<? super V> filter){
		Objects.requireNonNull(map, "Map");
		Objects.requireNonNull(filter, "Predicate");
		return map.values().stream().filter(filter).findFirst();
	}
	
	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param filter
	 * @return
	 */
	static <K, V> Optional<V> findAny(final Map<K, V> map, final Predicate<? super V> filter){
		Objects.requireNonNull(map, "Map");
		Objects.requireNonNull(filter, "Predicate");
		return map.values().parallelStream().filter(filter).findAny();
	}
	
	/**
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	static <K, V> Optional<V> put(final Map<K, V> map, final K key, final V value){
		Objects.requireNonNull(map, "Map");
		return Optional.ofNullable(map.put(key, value));
	}
}
