package epf.shell;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.logging.Logger;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import epf.util.logging.LogManager;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Function
@Interceptor
@Priority(value = Interceptor.Priority.APPLICATION)
public class FunctionInterceptor {
	
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
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
			ex.printStackTrace(err);
			return null;
		}
	}
	
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
