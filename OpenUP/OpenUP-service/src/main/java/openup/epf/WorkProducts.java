/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/WorkProducts.java
package openup.epf.schema;
=======
package openup.epf;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/WorkProducts.java

import epf.schema.OpenUP;
import epf.schema.openup.Role;
import epf.schema.work_products.Domain;
import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/WorkProducts.java
import javax.ws.rs.Consumes;
=======
import javax.ws.rs.GET;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/WorkProducts.java
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
@Path("schema/work-products")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/WorkProducts.java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class WorkProducts implements openup.client.epf.schema.WorkProducts {
=======
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class WorkProducts {
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/WorkProducts.java
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/WorkProducts.java
    @Override
=======
    @GET
    @Produces(MediaType.APPLICATION_JSON)
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/WorkProducts.java
    @Operation(
            summary = "Work Products", 
            description = "List of work products organized by domain."
    )
    @APIResponse(
            description = "Domain",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = Domain.class)
            )
    )
    public List<Domain> getDomains() throws Exception{
        return cache.getNamedQueryResult(
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/WorkProducts.java
                "OpenUP",
=======
                OpenUP.Schema,
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/WorkProducts.java
                principal,
                Domain.DOMAINS, 
                Domain.class);
    }
}
