/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.model;

import openup.model.tasks.Discipline;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
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
@RequestScoped
@RolesAllowed("Any_Role")
public class Tasks {
    
    @Inject
    private EntityManager entityManager;
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Tasks", 
            description = "List of tasks organized by discipline."
    )
    @APIResponse(
            description = "Discipline",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Discipline.class)
            )
    )
    public List<Discipline> getDisciplines(){
        return entityManager.createNamedQuery("Discipline.Disciplines", Discipline.class).getResultList();
    }
}
