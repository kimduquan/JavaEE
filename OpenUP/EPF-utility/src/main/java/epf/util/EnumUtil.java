package epf.util;

/**
 * @author PC
 *
 */
public interface EnumUtil {

	/**
	 * @param <T>
	 * @param cls
	 * @param name
	 * @return
	 */
	static <T extends Enum<T>> T valueOf(final Class<T> cls, final String name, final boolean caseSensitive) {
		final T[] enums = cls.getEnumConstants();
		for(T e : enums) {
			if(caseSensitive && e.name().equals(name)) {
				return e;
			}
			if(!caseSensitive && e.name().toLowerCase().equals(name)) {
				return e;
			}
		}
		return null;
	}
}
