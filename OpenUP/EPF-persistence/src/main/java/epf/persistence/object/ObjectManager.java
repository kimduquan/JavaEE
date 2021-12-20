package epf.persistence.object;

import java.io.Closeable;

/**
 * @author PC
 *
 */
public interface ObjectManager extends Closeable {

	/**
	 * @param object
	 */
	void persist(final Object object);

	/**
	 * @param <T>
	 * @param object
	 * @return
	 */
	<T> T merge(final T object);

	/**
	 * @param object
	 */
	void remove(final Object object);

	/**
	 * @param <T>
	 * @param objectClass
	 * @param primaryKey
	 * @return
	 */
	<T> T find(final Class<T> objectClass, final Object primaryKey);

	/**
	 * @param object
	 */
	void refresh(final Object object);

	/**
	 * @param object
	 * @return
	 */
	boolean contains(final Object object);

	/**
	 * @param <T>
	 * @param qlString
	 * @param resultClass
	 * @return
	 */
	<T> ObjectQuery<T> createQuery(final String qlString, final Class<T> resultClass);

	/**
	 * @return
	 */
	boolean isOpen();
}
