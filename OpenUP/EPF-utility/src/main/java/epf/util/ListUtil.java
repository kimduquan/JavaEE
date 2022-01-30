package epf.util;

import java.util.ArrayList;
import java.util.List;

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
}
