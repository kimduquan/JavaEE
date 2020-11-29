/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.epf.schema;

import epf.schema.roles.RoleSet;
import java.security.Principal;
import java.util.List;
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
@Path("roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class Roles implements openup.api.epf.schema.Roles {
    
    @Inject
    private Cache cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @Override
    @Operation(
            summary = "Roles", 
            description = "This category lists roles organized by role set."
    )
    @APIResponse(
            description = "Role Set",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = RoleSet.class)
            )
    )
    public List<RoleSet> getRoleSets() throws Exception{
        return cache.getNamedQueryResult(
                principal,
                RoleSet.ROLES, 
                RoleSet.class);
    }
}
