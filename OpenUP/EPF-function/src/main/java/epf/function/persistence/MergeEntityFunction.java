package epf.function.persistence;

import javax.ws.rs.HttpMethod;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class MergeEntityFunction extends EntityFunction {

	/**
	 * 
	 */
	public MergeEntityFunction() {
		super(Naming.PERSISTENCE, HttpMethod.PUT, "{schema}/{entity}/{id}");
	}
}
