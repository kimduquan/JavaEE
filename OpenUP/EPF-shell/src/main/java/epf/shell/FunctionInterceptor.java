package epf.shell;

import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ValidationException;

/**
 * @author PC
 *
 */
@Function
@Interceptor
@Priority(value = Interceptor.Priority.APPLICATION)
@ApplicationScoped
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
	 * 
	 */
	//@Inject
	//transient ExecutableValidator validator;
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext context) throws Exception {
		Object result = null;
		try {
			//validator.validateParameters(context.getTarget(), context.getMethod(), context.getParameters());
			result = invoke(context);
		}
		catch(ValidationException ex) {
			err.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	protected Object invoke(final InvocationContext context) throws Exception {
		final String cls = context.getTarget().getClass().getName();
		final Logger logger = Logger.getLogger(cls);
		final String method = context.getMethod().getName();
		Object result = null;
		try {
			logger.entering(cls, method, context.getParameters());
			final long time = System.currentTimeMillis();
			result = context.proceed();
			final long duration = System.currentTimeMillis() - time;
			logger.exiting(cls, method, result);
			out.println(String.format("Proceed.(%dms)", duration));
			final Class<?> returnType = context.getMethod().getReturnType();
			if(returnType != null && !returnType.equals(void.class)) {
				out.println(valueOf(result));
			}
		}
		catch(Exception ex) {
			err.println(ex.getMessage());
			logger.throwing(cls, method, ex);
		}
		return result;
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
