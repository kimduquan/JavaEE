package epf.function;

import javax.ws.rs.core.Link;

/**
 * @author PC
 *
 */
public interface LinkFunction {
	
	/**
	 * @param index
	 * @return
	 */
	Link toLink(final Integer index);
}
