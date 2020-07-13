/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.delivery_processes;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("delivery-processes")
@RequestScoped
public class DeliveryProcesses {
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Delivery Processes", 
            description = "This provides a list of delivery processes that have been published."
    )
    @APIResponse(
            description = "Delivery Process",
            content = @Content(
                    schema = @Schema(implementation = DeliveryProcess.class)
            )
    )
    public DeliveryProcess[] getDeliveryProcesses(){
        return new DeliveryProcess[]{};
    }
}
