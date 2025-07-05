package epf.function.query;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

public class FetchEntitiesFunction extends Function {

	public FetchEntitiesFunction() {
		super(Naming.QUERY, HttpMethod.PATCH, Naming.Query.Client.ENTITY);
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
