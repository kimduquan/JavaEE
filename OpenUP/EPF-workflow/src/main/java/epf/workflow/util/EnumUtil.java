package epf.workflow.util;

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
	static <T extends Enum<T>> T valueOf(final Class<T> cls, final String name) {
		final T[] enums = cls.getEnumConstants();
		for(T e : enums) {
			if(e.name().toLowerCase().equals(name)) {
				return e;
			}
		}
		return null;
	}
}
