package epf.util;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
}
