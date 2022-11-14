package epf.client.util;

import java.time.Duration;
import javax.ws.rs.core.Link;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public interface LinkUtil {
    
    /**
     * @param service
     * @param path
     * @param method
     * @param index
     * @param wait
     * @param values
     * @return
     */
    static Link link(final String service, final String path, final String method, final Integer index, final Duration wait, final Object... values) {
    	Link.Builder builder = Link.fromPath(path);
    	builder = builder.type(method);
    	builder = builder.rel(service);
    	if(index != null) {
    		builder = builder.title("" + index);
    	}
    	if(wait != null) {
        	builder = builder.param(Naming.Client.Internal.LINK_PARAM_WAIT, wait.toString());
    	}
    	return builder.build(values);
    }
}
