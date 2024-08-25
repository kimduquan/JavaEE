package epf.function.persistence;

import javax.ws.rs.HttpMethod;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class RemoveEntityFunction extends EntityFunction {

	/**
	 * 
	 */
	public RemoveEntityFunction() {
		super(Naming.PERSISTENCE, HttpMethod.DELETE, "{schema}/{entity}/{id}");
	}
}
