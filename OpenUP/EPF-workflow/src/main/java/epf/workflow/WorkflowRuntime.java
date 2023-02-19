package epf.workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath(Naming.WORKFLOW)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkflowRuntime {

}
