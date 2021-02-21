package epf.util.logging;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;
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
	
	@Inject
	private Factory factory;
	
	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		String cls = invocationContext.getMethod().getDeclaringClass().getName();
		Logger logger = factory.getLogger(cls);
		logger.entering(cls, invocationContext.getMethod().getName(), invocationContext.getParameters());
		Object result = invocationContext.proceed();
		logger.exiting(cls, invocationContext.getMethod().getName(), result);
		return result;
	}
}
