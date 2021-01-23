/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Roles.java
package openup.epf.schema;
=======
package openup.epf;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Roles.java

import epf.schema.OpenUP;
import epf.schema.openup.Role;
import epf.schema.roles.RoleSet;
import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Roles.java
import javax.ws.rs.Consumes;
=======
import javax.ws.rs.GET;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Roles.java
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
@Path("schema/roles")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Roles.java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class Roles implements openup.client.epf.schema.Roles {
=======
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class Roles {
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Roles.java
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Roles.java
    @Override
=======
    @GET
    @Produces(MediaType.APPLICATION_JSON)
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Roles.java
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
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Roles.java
                "OpenUP",
=======
                OpenUP.Schema,
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Roles.java
                principal,
                RoleSet.ROLES, 
                RoleSet.class);
    }
}
