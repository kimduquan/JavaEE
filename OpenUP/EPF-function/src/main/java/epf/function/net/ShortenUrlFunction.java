package epf.function.net;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class ShortenUrlFunction extends Function {

	/**
	 * 
	 */
	public ShortenUrlFunction() {
		super(Naming.NET, HttpMethod.PUT, "url");
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
