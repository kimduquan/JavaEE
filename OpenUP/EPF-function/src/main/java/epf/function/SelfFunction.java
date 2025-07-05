package epf.function;

import jakarta.ws.rs.core.Link;
import epf.naming.Naming;

public class SelfFunction extends Function {

	public SelfFunction(final String method) {
		super(Naming.Client.Link.SELF, method, "");
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}
}
