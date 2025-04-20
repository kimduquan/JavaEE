package epf.function.persistence;

import jakarta.ws.rs.HttpMethod;
import epf.naming.Naming;

public class RemoveEntityFunction extends EntityFunction {

	public RemoveEntityFunction() {
		super(Naming.PERSISTENCE, HttpMethod.DELETE, "{schema}/{entity}/{id}");
	}
}
