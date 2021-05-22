package epf.shell;

import java.io.PrintWriter;
import java.util.logging.Logger;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/**
 * @author PC
 *
 */
@Function
@Interceptor
@Priority(value = Interceptor.Priority.APPLICATION)
public class FunctionInterceptor {
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	private transient PrintWriter out;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.ERR)
	private transient PrintWriter err;
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext context) throws Exception {
		final String cls = context.getTarget().getClass().getName();
		final String method = context.getMethod().getName();
		final Logger logger = Logger.getLogger(cls);
		Object result = null;
		try {
			logger.entering(cls, method, context.getParameters());
			result = context.proceed();
			logger.exiting(cls, method, result);
			out.println();
			out.println(String.valueOf(result));
		}
		catch(Exception ex) {
			err.println(ex.getMessage());
			logger.throwing(cls, method, ex);
		}
		return result;
	}

}
