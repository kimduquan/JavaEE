package epf.workflow.util;

import jakarta.el.ELProcessor;
import jakarta.json.JsonValue;

public class Processor {

	private final ELProcessor elProcessor;
	private final JsonELResolver elResolver;
	
	public Processor(final JsonValue data) {
		elProcessor = new ELProcessor();
		elResolver = new JsonELResolver(data);
		elResolver.defineBean(elProcessor);
		elProcessor.getELManager().addELResolver(elResolver);
	}
	
	public Object eval(final String expression) {
		return elProcessor.eval(expression);
	}
}
