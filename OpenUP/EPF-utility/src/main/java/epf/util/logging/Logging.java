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
	private static final Map<String, Logger> loggers = new ConcurrentHashMap<>();
	
	/**
	 * @param invocationContext
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object logMethodEntry(final InvocationContext invocationContext) throws Exception {
		final String cls = invocationContext.getMethod().getDeclaringClass().getName();
		final Logger logger = getLogger(cls);
		logger.entering(cls, invocationContext.getMethod().getName(), invocationContext.getParameters());
		final Object result = invocationContext.proceed();
		logger.exiting(cls, invocationContext.getMethod().getName(), result);
		return result;
	}
	
	/**
	 * @param clsName
	 * @return
	 */
	public static Logger getLogger(final String clsName) {
		return loggers.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
}
