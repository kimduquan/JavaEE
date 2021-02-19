/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.schema;

import openup.schema.OpenUP;
import java.util.List;
import epf.schema.delivery_processes.DeliveryProcess;
import openup.schema.Role;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import openup.persistence.Request;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("schema/delivery-processes")
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class DeliveryProcesses {
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delivery Processes", 
            description = "This provides a list of delivery processes that have been published."
    )
    @APIResponse(
            description = "Delivery Process",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = DeliveryProcess.class)
            )
    )
    public List<DeliveryProcess> getDeliveryProcesses() throws Exception{
        return cache.getNamedQueryResult(
                OpenUP.Schema,
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
