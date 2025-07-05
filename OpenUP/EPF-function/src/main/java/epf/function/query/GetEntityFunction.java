package epf.function.query;

import jakarta.ws.rs.HttpMethod;
import epf.function.persistence.EntityFunction;
import epf.naming.Naming;

public class GetEntityFunction extends EntityFunction {

	public GetEntityFunction() {
		super(Naming.QUERY, HttpMethod.GET, Naming.Query.Client.ENTITY_PATH);
	}
}
