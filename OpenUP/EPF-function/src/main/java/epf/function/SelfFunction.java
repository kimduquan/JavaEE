package epf.function;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class SelfFunction extends Function {

	/**
	 * 
	 */
	public SelfFunction() {
		super(Naming.Client.Link.SELF, HttpMethod.POST, "");
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}
}
