/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch;

import java.util.List;
import java.util.Properties;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("batch/excutions")
public interface Executions {
    
    @DELETE
    void abandon(@QueryParam("executionId") long executionId);
    
    @GET
    @Path("{executionId}")
    @Produces(MediaType.APPLICATION_JSON)
    JobExecution getJobExecution(@PathParam("executionId") long executionId);
    
    @PUT
    @Path("{executionId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    long restart(@PathParam("executionId") long executionId, @BeanParam Properties props);
    
    @DELETE
    @Path("{executionId}")
    void stop(@PathParam("executionId") long executionId);
    
    @GET
    @Path("{jobExecutionId}/steps")
    @Produces(MediaType.APPLICATION_JSON)
    List<StepExecution> getStepExecutions(@PathParam("jobExecutionId") long jobExecutionId);
}
