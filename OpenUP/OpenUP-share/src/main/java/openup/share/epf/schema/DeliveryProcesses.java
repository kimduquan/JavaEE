/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.share.epf.schema;

import java.util.List;
import epf.schema.delivery_processes.DeliveryProcess;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@Path("delivery-processes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
public interface DeliveryProcesses {
    
    @GET
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
    List<DeliveryProcess> getDeliveryProcesses() throws Exception;
}
