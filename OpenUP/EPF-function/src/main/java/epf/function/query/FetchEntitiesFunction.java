package epf.function.query;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class FetchEntitiesFunction extends Function {

	/**
	 * 
	 */
	public FetchEntitiesFunction() {
		super(Naming.QUERY, HttpMethod.PATCH, Naming.Query.Client.ENTITY);
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build();
	}

}
