/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("batch/job")
public interface Jobs {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Set<String> getJobNames();
    
    @GET
    @Path("{jobName}")
    @Produces(MediaType.APPLICATION_JSON)
    List<JobInstance> getJobInstances(@PathParam("{jobName}") String jobName, @QueryParam("start") int start, @QueryParam("count") int count);
    
    @GET
    @Path("{jobName}/{instanceId}")
    List<JobExecution> getJobExecutions(@PathParam("{jobName}") String jobName, @PathParam("{instanceId}") long instanceId);
    
    @POST
    @Path("{jobXMLName}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    long start(@PathParam("{jobXMLName}") String jobXMLName, @BeanParam Properties jobParameters);
}
