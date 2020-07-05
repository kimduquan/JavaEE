/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.work_products;

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
@Path("work-products")
@RequestScoped
public class WorkProducts {
    
    @Name("Contents")
    @GET
    @Operation(
            summary = "Work Products", 
            description = "List of work products organized by domain."
    )
    @APIResponse(
            description = "Domain",
            content = @Content(
                    schema = @Schema(implementation = Domain.class)
            )
    )
    public Domain[] getDomains(){
        return new Domain[]{};
    }
}
