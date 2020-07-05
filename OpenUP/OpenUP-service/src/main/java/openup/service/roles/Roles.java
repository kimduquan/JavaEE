/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.roles;

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
@Path("roles")
@RequestScoped
public class Roles {
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Roles", 
            description = "This category lists roles organized by role set."
    )
    @APIResponse(
            description = "Role Set",
            content = @Content(
                    schema = @Schema(implementation = RoleSet.class)
            )
    )
    public RoleSet[] getRoleSets(){
        return new RoleSet[]{};
    }
}
