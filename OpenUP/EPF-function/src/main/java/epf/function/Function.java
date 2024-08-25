package epf.function;

import java.time.Duration;
import javax.ws.rs.core.Link;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public abstract class Function implements LinkFunction {
	
	/**
	 * 
	 */
	private final String service;
	
	/**
	 * 
	 */
	private final String path;
	
	/**
	 * 
	 */
	private final String method;
	
	/**
	 * 
	 */
	private Duration wait;
	
	/**
	 * @param service
	 * @param method
	 * @param path
	 */
	public Function(final String service, final String method, final String path) {
		this.path = path;
		this.method = method;
		this.service = service;
	}
	
	/**
	 * @return
	 */
	protected Link.Builder buildLink(final Integer index) {
		Link.Builder builder = Link.fromPath(path).type(method).rel(service);
		if(index != null) {
    		builder = builder.title("" + index);
    	}
    	if(wait != null) {
        	builder = builder.param(Naming.Client.Link.WAIT, wait.toString());
    	}
		return builder;
	}

	public Duration getWait() {
		return wait;
	}

	public void setWait(final Duration wait) {
		this.wait = wait;
	}
}
