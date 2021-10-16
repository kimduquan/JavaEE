package epf.util.logging;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author PC
 *
 */
@Log
@Interceptor
public class LogManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>();
	
	/**
	 * @param invocationContext
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object logMethodEntry(final InvocationContext invocationContext) {
		final String cls = invocationContext.getMethod().getDeclaringClass().getName();
		final String method = invocationContext.getMethod().getName();
		final Logger logger = getLogger(cls);
		logger.entering(cls, method, invocationContext.getParameters());
		Object result = null;
		try {
			result = invocationContext.proceed();
		} 
		catch (Exception e) {
			logger.throwing(cls, method, e);
		}
		finally {
			logger.exiting(cls, invocationContext.getMethod().getName(), result);
		}
		return result;
	}
	
	/**
	 * @param clsName
	 * @return
	 */
	public static Logger getLogger(final String clsName) {
		Objects.requireNonNull(clsName);
		return LOGGERS.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
	
	/**
	 * @param cls
	 */
	public static void config(final Class<?> cls) {
		final String configFile = System.getProperty("java.util.logging.config.file");
		if(configFile == null) {
			final URL config = cls.getClassLoader().getResource("logging.properties");
			if(config != null) {
				System.setProperty("java.util.logging.config.file", config.getFile());
			}
		}
	}
}
