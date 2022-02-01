package epf.persistence.client;

import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.PERSISTENCE)
public interface Persistence extends Entities, Queries {

}
