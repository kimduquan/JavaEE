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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("schema/delivery-processes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class DeliveryProcesses implements openup.client.epf.schema.DeliveryProcesses {
    
    @Inject
    private Cache cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @Override        
    @Operation(
            summary = "Delivery Processes", 
            description = "This provides a list of delivery processes that have been published."
    )
    @APIResponse(
            description = "Delivery Process",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DeliveryProcess.class)
            )
    )
    public List<DeliveryProcess> getDeliveryProcesses() throws Exception{
        return cache.getNamedQueryResult(
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
