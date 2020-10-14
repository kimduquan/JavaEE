/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import epf.schema.tasks.Discipline;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import openup.persistence.Request;
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
@Path("tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@RolesAllowed(Roles.ANY_ROLE)
public class Tasks {
    
    @Inject
    private Request request;
    
    @Context
    private SecurityContext context;
    
    private EntityManager manager;
    
    @PostConstruct
    void postConstruct(){
        manager = request.getManager(context);
    }
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Tasks", 
            description = "List of tasks organized by discipline."
    )
    @APIResponse(
            description = "Discipline",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Discipline.class)
            )
    )
    public List<Discipline> getDisciplines(){
        return manager.createNamedQuery(
                Discipline.DISCIPLINES, 
                Discipline.class)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
