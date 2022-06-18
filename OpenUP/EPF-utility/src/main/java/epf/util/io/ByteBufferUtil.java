package epf.util.io;

import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;

/**
 * 
 */
public interface ByteBufferUtil {
	
	/**
	 *
	 */
	String CLEANER_METHOD = "cleaner";
	
	/**
	 *
	 */
	String CLEANER_CLEAR_METHOD = "clear";

	/**
	 * @param buffer
	 * @throws Exception
	 */
	static void clean(final MappedByteBuffer buffer) throws Exception {
		final Method cleanerMethod = buffer.getClass().getMethod(CLEANER_METHOD);
		final Object cleaner = (Object) cleanerMethod.invoke(buffer);
		final Method clearMethod = cleaner.getClass().getMethod(CLEANER_CLEAR_METHOD);
		clearMethod.invoke(cleaner);
	}
}
