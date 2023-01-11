package epf.function.cache;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class GetFunction extends Function {

	/**
	 * @param path
	 */
	public GetFunction(final String... path) {
		super(Naming.CACHE, HttpMethod.GET, Naming.CACHE + "/" + String.join("/", path));
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
