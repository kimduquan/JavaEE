package epf.shell;

import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.validation.ValidationException;
import jakarta.validation.executable.ExecutableValidator;

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
	 * 
	 */
	@Inject
	private transient ExecutableValidator validator;
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext context) throws Exception {
		Object result = null;
		try {
			validator.validateParameters(context.getTarget(), context.getMethod(), context.getParameters());
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
		final String method = context.getMethod().getName();
		final Logger logger = Logger.getLogger(cls);
		Object result = null;
		try {
			logger.entering(cls, method, context.getParameters());
			result = context.proceed();
			logger.exiting(cls, method, result);
			if(!context.getMethod().getReturnType().equals(Void.class)) {
				out.println();
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
		if(!(result instanceof String)) {
			try(Jsonb jsonb = JsonbBuilder.create()){
				value = jsonb.toJson(result);
			}
		}
		else {
			value = String.valueOf(result);
		}
		return value;
	}
}
