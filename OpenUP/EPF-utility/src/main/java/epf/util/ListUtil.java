package epf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author PC
 *
 */
public interface ListUtil {

	/**
	 * @param <T>
	 * @param list
	 * @param element
	 * @return
	 */
	static <T> List<T> add(final List<T> list, final T element){
		final List<T> newList = new ArrayList<>(list);
		newList.add(element);
		return newList;
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param filter
	 * @return
	 */
	static <T> Optional<T> findFirst(final T[] list, Predicate<T> filter){
		return Arrays.asList(list).stream().filter(filter).findFirst();
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param filter
	 * @return
	 */
	static <T> Optional<T> findFirst(final Iterator<T> list, Predicate<T> filter){
		Optional<T> first = Optional.empty();
		while(list.hasNext()) {
			final T value = list.next();
			if(filter.test(value)) {
				first = Optional.ofNullable(value);
				break;
			}
		}
		return first;
	}
}
