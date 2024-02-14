package epf.workflow.util;

import java.util.List;

import epf.nosql.schema.StringOrArray;
import epf.nosql.schema.StringOrObject;

/**
 * 
 */
public interface EitherUtil {

	/**
	 * @param <T>
	 * @param either
	 * @return
	 */
	static <T> T getObject(final StringOrObject<T> either) {
		return either.getRight();
	}
	
	/**
	 * @param <T>
	 * @param either
	 * @return
	 */
	static <T> List<T> getArray(final StringOrArray<T> either){
		return either.getRight();
	}
}
