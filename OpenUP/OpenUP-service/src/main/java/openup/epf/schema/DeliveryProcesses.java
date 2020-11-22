/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.epf.schema;

import java.util.List;
import epf.schema.delivery_processes.DeliveryProcess;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import openup.persistence.Cache;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("delivery-processes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(openup.api.epf.schema.Roles.ANY_ROLE)
@RequestScoped
public class DeliveryProcesses implements openup.api.epf.schema.DeliveryProcesses {
    
    @Inject
    private Cache cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @Override
    public List<DeliveryProcess> getDeliveryProcesses() throws Exception{
        return cache.getNamedQueryResult(
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
