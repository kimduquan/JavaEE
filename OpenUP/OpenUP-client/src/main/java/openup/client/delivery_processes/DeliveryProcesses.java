/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.delivery_processes;

import openup.schema.DeliveryProcess;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("delivery-processes")
public interface DeliveryProcesses {
    
    @POST
    @Path("{delivery-process}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    DeliveryProcess newDeliveryProcess(
            @PathParam("delivery-process")
            @NotBlank
            String delivery_process,
            @FormParam("name")
            @NotBlank
            String name,
            @FormParam("summary")
            String summary
    ) throws Exception;
}
