/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.tasks;

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
@Path("tasks")
@RequestScoped
public class Tasks {
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Tasks", 
            description = "List of tasks organized by discipline."
    )
    @APIResponse(
            description = "Discipline",
            content = @Content(
                    schema = @Schema(implementation = Discipline.class)
            )
    )
    public Discipline[] getDisciplines(){
        return new Discipline[]{};
    }
}
