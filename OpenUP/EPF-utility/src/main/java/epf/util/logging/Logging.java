package epf.util.logging;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class Logging implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<String, Logger> loggers = new ConcurrentHashMap<>();
	
	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		String cls = invocationContext.getMethod().getDeclaringClass().getName();
		Logger logger = getLogger(cls);
		logger.entering(cls, invocationContext.getMethod().getName(), invocationContext.getParameters());
		Object result = invocationContext.proceed();
		logger.exiting(cls, invocationContext.getMethod().getName(), result);
		return result;
	}
	
	public static Logger getLogger(String clsName) {
		return loggers.computeIfAbsent(clsName, name -> {
			return Logger.getLogger(name);
		});
	}
}
