package epf.function.net;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

public class ShortenUrlFunction extends Function {

	public ShortenUrlFunction() {
		super(Naming.NET, HttpMethod.PUT, Naming.Net.URL);
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
