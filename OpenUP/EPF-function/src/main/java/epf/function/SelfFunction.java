package epf.function;

import javax.ws.rs.core.Link;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class SelfFunction extends Function {

	/**
	 * @param method
	 */
	public SelfFunction(final String method) {
		super(Naming.Client.Link.SELF, method, "");
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}
}
