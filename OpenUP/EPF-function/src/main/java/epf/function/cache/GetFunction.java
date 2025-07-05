package epf.function.cache;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

public class GetFunction extends Function {

	public GetFunction(final String... path) {
		super(Naming.CACHE, HttpMethod.GET, Naming.CACHE + "/" + String.join("/", path));
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
