/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.api;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("batch")
public interface Batch {
    
    @GET
    @Path("jobinstances")
    @Produces(MediaType.APPLICATION_JSON)
    List<JobInstance> getJobInstances(
                    @QueryParam("page") 
                    Integer page, 
                    @QueryParam("pageSize")
                    Integer pageSize
    ) throws Exception;
    
    @GET
    @Path("v2/jobinstances")
    @Produces(MediaType.APPLICATION_JSON)
    List<JobInstance> getJobInstances(
                    @QueryParam("jobInstanceId")
                    String jobInstanceId,
                    @QueryParam("createTime")
                    String createTime,
                    @QueryParam("instanceState")
                    InstanceState[] instanceState,
                    @QueryParam("exitStatus")
                    String exitStatus,
                    @QueryParam("page") 
                    Integer page, 
                    @QueryParam("pageSize")
                    Integer pageSize
    ) throws Exception;
    
    @GET
    @Path("v3/jobinstances")
    @Produces(MediaType.APPLICATION_JSON)
    List<JobInstance> getJobInstances(
                    @QueryParam("jobInstanceId")
                    String jobInstanceId,
                    @QueryParam("createTime")
                    String createTime,
                    @QueryParam("instanceState")
                    InstanceState[] instanceState,
                    @QueryParam("exitStatus")
                    String exitStatus,
                    @QueryParam("page") 
                    Integer page, 
                    @QueryParam("pageSize")
                    Integer pageSize,
                    @QueryParam("lastUpdatedTime")
                    String lastUpdatedTime,
                    @QueryParam("sort")
                    String[] sort
    ) throws Exception;
    
    @GET
    @Path("v4/jobinstances")
    @Produces(MediaType.APPLICATION_JSON)
    List<JobInstance> getJobInstances(
                    @QueryParam("jobInstanceId")
                    String jobInstanceId,
                    @QueryParam("createTime")
                    String createTime,
                    @QueryParam("instanceState")
                    InstanceState[] instanceState,
                    @QueryParam("exitStatus")
                    String exitStatus,
                    @QueryParam("page") 
                    Integer page, 
                    @QueryParam("pageSize")
                    Integer pageSize,
                    @QueryParam("lastUpdatedTime")
                    String lastUpdatedTime,
                    @QueryParam("sort")
                    String[] sort,
                    @QueryParam("submitter")
                    String submitter,
                    @QueryParam("appName")
                    String appName,
                    @QueryParam("jobName")
                    String jobName,
                    @QueryParam("ignoreCase")
                    Boolean ignoreCase
    ) throws Exception;
    
    @POST
    @Path("jobinstances/{job_instance_id}")
    @Produces(MediaType.APPLICATION_JSON)
    JobInstance getJobInstance(
                    @PathParam("job_instance_id") 
                    long job_instance_id
    ) throws Exception;
    
    @PUT
    @Path("jobinstances/{job_instance_id}")
    void doAction(
                    @PathParam("job_instance_id") 
                    long job_instance_id, 
                    @QueryParam("action") 
                    String action,
                    @QueryParam("reusePreviousParams")
                    Boolean reusePreviousParams
    ) throws Exception;
    
    @DELETE
    @Path("jobinstances/{job_instance_id}")
    void stopJobIntance(
                    @PathParam("job_instance_id") 
                    long job_instance_id, 
                    @QueryParam("purgeJobStoreOnly") 
                    Boolean purgeJobStoreOnly
    ) throws Exception;
    
    @DELETE
    @Path("v2/jobinstances")
    @Produces(MediaType.APPLICATION_JSON)
    List<PurgeResult> stopJobInstances(
                    @QueryParam("page") 
                    Integer page,
                    @QueryParam("pageSize")
                    Integer pageSize,
                    @QueryParam("purgeJobStoreOnly")
                    boolean purgeJobStoreOnly,
                    @QueryParam("jobInstanceId")
                    String jobInstanceId,
                    @QueryParam("createTime")
                    String createTime,
                    @QueryParam("instanceState")
                    InstanceState instanceState,
                    @QueryParam("exitStatus")
                    String exitStatus
    ) throws Exception;
    
    @GET
    @Path("jobexecutions/{job_execution_id}")
    @Produces(MediaType.APPLICATION_JSON)
    JobExecution getJobExecution(
                    @PathParam("job_execution_id") 
                    long job_execution_id
    ) throws Exception;
    
    @GET
    @Path("jobexecutions/{job_execution_id}/jobinstance")
    @Produces(MediaType.APPLICATION_JSON)
    Object getJobInstanceDetail(
                    @PathParam("job_execution_id") 
                    long job_execution_id
    ) throws Exception;
    
    @GET
    @Path("jobinstances/{job_instance_id}/jobexecutions")
    @Produces(MediaType.APPLICATION_JSON)
    Object getJobExecutionDetail(
                    @PathParam("job_instance_id") 
                    long job_instance_id
    ) throws Exception;
    
    @GET
    @Path("jobinstances/{job_instance_id}/jobexecutions/{job_execution_sequence_number}")
    @Produces(MediaType.APPLICATION_JSON)
    Object getJobExecutionDetail(
                    @PathParam("job_instance_id") 
                    long job_instance_id, 
                    @PathParam("job_execution_sequence_number") 
                    long job_execution_sequence_number
    ) throws Exception;
    
    @PUT
    @Path("jobexecutions/{job_execution_id}")
    void stopExecution(
                    @PathParam("job_execution_id") 
                    long job_execution_id, 
                    @QueryParam("action") 
                    String action
    ) throws Exception;
    
    @GET
    @Path("jobexecutions/{job_execution_id}/stepexecutions")
    @Produces(MediaType.APPLICATION_JSON)
    List<StepExecutionDetail> getStepExecutions(
                    @PathParam("job_execution_id") 
                    long job_execution_id
    ) throws Exception;
    
    @GET
    @Path("jobexecutions/{job_execution_id}/stepexecutions/{step_name}")
    @Produces(MediaType.APPLICATION_JSON)
    List<StepExecutionDetail> getStepExecutionDetails(
                    @PathParam("job_execution_id") 
                    long job_execution_id, 
                    @PathParam("step_name") 
                    String step_name
    ) throws Exception;
    
    @GET
    @Path("jobinstances/{job_instance_id}/jobexecutions/{job_execution_sequence_number}/stepexecutions/{step_name}")
    @Produces(MediaType.APPLICATION_JSON)
    List<StepExecutionDetail> getStepExecutionDetails(
                    @PathParam("job_instance_id") 
                    long job_instance_id, 
                    @PathParam("job_execution_sequence_number") 
                    long job_execution_sequence_number, 
                    @PathParam("step_name") String step_name
    ) throws Exception;
    
    @GET
    @Path("stepexecutions/{step_execution_id}")
    @Produces(MediaType.APPLICATION_JSON)
    List<StepExecutionDetail> getStepExecutionDetails(
                    @PathParam("step_execution_id") 
                    long step_execution_id
    ) throws Exception;
    
    @GET
    @Path("jobinstances/{job_instance_id}/joblogs")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getJobInstanceLogs(
                    @PathParam("job_instance_id")
                    long job_instance_id,
                    @QueryParam("type")
                    String type,
                    @QueryParam("part")
                    String part
    ) throws Exception;
    
    @GET
    @Path("jobexecutions/{job_execution_id}/joblogs")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getJobExecutionLogs(
                    @PathParam("job_execution_id")
                    long job_execution_id,
                    @QueryParam("type")
                    String type,
                    @QueryParam("part")
                    String part
    ) throws Exception;
}
