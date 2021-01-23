/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/DeliveryProcesses.java
package openup.epf.schema;
=======
package openup.epf;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/DeliveryProcesses.java

import epf.schema.OpenUP;
import java.util.List;
import epf.schema.delivery_processes.DeliveryProcess;
import epf.schema.openup.Role;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/DeliveryProcesses.java
import javax.ws.rs.Consumes;
=======
import javax.ws.rs.GET;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/DeliveryProcesses.java
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import openup.persistence.Request;
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
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/DeliveryProcesses.java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class DeliveryProcesses implements openup.client.epf.schema.DeliveryProcesses {
=======
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class DeliveryProcesses {
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/DeliveryProcesses.java
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/DeliveryProcesses.java
    @Override        
=======
    @GET
    @Produces(MediaType.APPLICATION_JSON)
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/DeliveryProcesses.java
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
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/DeliveryProcesses.java
                "OpenUP",
=======
                OpenUP.Schema,
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/DeliveryProcesses.java
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
