/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Tasks.java
package openup.epf.schema;
=======
package openup.epf;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Tasks.java

import epf.schema.OpenUP;
import epf.schema.openup.Role;
import epf.schema.tasks.Discipline;
import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Tasks.java
import javax.ws.rs.Consumes;
=======
import javax.ws.rs.GET;
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Tasks.java
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
@Path("schema/tasks")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Tasks.java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class Tasks implements openup.client.epf.schema.Tasks {
=======
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class Tasks {
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Tasks.java
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Tasks.java
    @Override
=======
    @GET
    @Produces(MediaType.APPLICATION_JSON)
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Tasks.java
    @Operation(
            summary = "Tasks", 
            description = "List of tasks organized by discipline."
    )
    @APIResponse(
            description = "Discipline",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = Discipline.class)
            )
    )
    public List<Discipline> getDisciplines() throws Exception{
        return cache.getNamedQueryResult(
<<<<<<< HEAD:OpenUP/OpenUP-service/src/main/java/openup/epf/schema/Tasks.java
                "OpenUP",
=======
                OpenUP.Schema,
>>>>>>> remotes/origin/micro:OpenUP/OpenUP-service/src/main/java/openup/epf/Tasks.java
                principal,
                Discipline.DISCIPLINES, 
                Discipline.class);
    }
}
