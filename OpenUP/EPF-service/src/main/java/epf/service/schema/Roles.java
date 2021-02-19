/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.schema;

import epf.schema.roles.RoleSet;
import epf.service.persistence.Request;

import java.security.Principal;
import java.util.List;
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
import epf.schema.EPF;
import epf.schema.roles.Role;

/**
 *
 * @author FOXCONN
 */
@Type
@Path("schema/roles")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Roles {
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    public List<RoleSet> getRoleSets() throws Exception{
        return cache.getNamedQueryResult(
                EPF.Schema,
                principal,
                RoleSet.ROLES, 
                RoleSet.class);
    }
}
