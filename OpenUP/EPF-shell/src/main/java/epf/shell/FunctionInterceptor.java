package epf.shell;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import epf.util.logging.LogManager;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

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
	transient PrintWriter out;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext context) throws Exception {
		final String cls = context.getTarget().getClass().getName();
		final Logger logger = LogManager.getLogger(cls);
		final String method = context.getMethod().getName();
		try {
			logger.entering(cls, method, context.getParameters());
			final long time = Instant.now().toEpochMilli();
			final Object result = context.proceed();
			final long duration = Instant.now().toEpochMilli() - time;
			logger.exiting(cls, method, result);
			out.println(String.format("Proceed.(%dms)", duration));
			final Class<?> returnType = context.getMethod().getReturnType();
			if(returnType != null && !returnType.getName().equals(void.class.getName())) {
				out.println(valueOf(result));
			}
			return result;
		}
		catch(Exception ex) {
			logger.log(Level.SEVERE, method, ex);
			err.println(ex.getMessage());
			return null;
		}
	}
	
	/**
	 * @param result
	 * @return
	 * @throws Exception 
	 */
	protected String valueOf(final Object result) throws Exception {
		String value;
		if(result instanceof String || result instanceof JsonValue || result == null) {
			value = String.valueOf(result);
		}
		else {
			try(Jsonb jsonb = JsonbBuilder.create()){
				value = jsonb.toJson(result);
			}
		}
		return value;
	}
}
