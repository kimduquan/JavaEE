/**
 * 
 */
package epf.util.logging;

import java.util.logging.Logger;

/**
 * @author PC
 *
 */
public interface LoggerUtil {

	/**
	 * @param runnable
	 * @throws Exception
	 */
	static void log(final Runnable runnable) throws Exception {
		final String cls = runnable.getClass().getName();
		final String method = "run";
		final Logger logger = LogManager.getLogger(cls);
		logger.entering(cls, method);
		try {
			runnable.run();
		}
		catch(Exception ex) {
			logger.throwing(cls, method, ex);
			throw ex;
		}
		finally {
			logger.exiting(cls, method);
		}
	}
}
