/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.schema;

import java.util.List;
import epf.schema.EPF;
import epf.schema.delivery_processes.DeliveryProcess;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.roles.Role;
import epf.service.persistence.Request;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("schema/delivery-processes")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class DeliveryProcesses {
    
    /**
     * 
     */
    @Inject
    private transient Request cache;
    
    /**
     * 
     */
    @Inject
    private transient Principal principal;
    
    /**
     * @return
     */
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
    public List<DeliveryProcess> getDeliveryProcesses() {
        return cache.getNamedQueryResult(
                EPF.Schema,
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
