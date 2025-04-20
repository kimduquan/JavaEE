package epf.function.persistence;

import jakarta.ws.rs.HttpMethod;
import epf.naming.Naming;

public class MergeEntityFunction extends EntityFunction {

	public MergeEntityFunction() {
		super(Naming.PERSISTENCE, HttpMethod.PUT, "{schema}/{entity}/{id}");
	}
}
