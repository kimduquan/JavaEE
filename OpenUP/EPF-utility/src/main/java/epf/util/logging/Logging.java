package epf.util.logging;

import java.io.Serializable;
import java.util.Map;
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
public class Logging implements Serializable {
	
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
		logger.exiting(cls, invocationContext.getMethod().getName(), result);
		return result;
	}
	
	/**
	 * @param clsName
	 * @return
	 */
	public static Logger getLogger(final String clsName) {
		return LOGGERS.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
}
