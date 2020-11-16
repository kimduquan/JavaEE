/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.api.epf.schema;

import epf.schema.roles.RoleSet;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
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
@Path("roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
public interface Roles {
    
    public static final String ANY_ROLE = "ANY_ROLE";
    public static final String ADMIN = "ADMIN";
    
    @GET
    @Operation(
            summary = "Roles", 
            description = "This category lists roles organized by role set."
    )
    @APIResponse(
            description = "Role Set",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = RoleSet.class)
            )
    )
    List<RoleSet> getRoleSets() throws Exception;
}
