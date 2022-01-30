/**
 * 
 */
package epf.client.batch.api;

import java.time.LocalDate;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.Batch.BATCH_API)
public interface Batch {

	/**
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@GET
	@Path("jobinstances")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstances(
			@QueryParam("page")
			@DefaultValue(value = "0")
			final Integer page,
			@QueryParam("pageSize")
			@DefaultValue(value = "50")
			final Integer pageSize
			);
	
	/**
	 * @param jobInstanceId
	 * @param createTime
	 * @param instanceState
	 * @param exitStatus
	 * @param page
	 * @param pageSize
	 */
	@GET
	@Path("v2/jobinstances")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstancesV2(
			@QueryParam("jobInstanceId")
			final String[] jobInstanceId,
			@QueryParam("createTime")
			final String createTime,
			@QueryParam("instanceState")
			final String[] instanceState,
			@QueryParam("exitStatus")
			final String exitStatus,
			@QueryParam("page")
			@DefaultValue(value = "0")
			final Integer page,
			@QueryParam("pageSize")
			@DefaultValue(value = "50")
			final Integer pageSize
			);
	
	/**
	 * @param jobInstanceId
	 * @param createTime
	 * @param instanceState
	 * @param exitStatus
	 * @param page
	 * @param pageSize
	 * @param lastUpdatedTime
	 * @param sort
	 */
	@GET
	@Path("v3/jobinstances")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstancesV3(
			@QueryParam("jobInstanceId")
			final String[] jobInstanceId,
			@QueryParam("createTime")
			final String createTime,
			@QueryParam("instanceState")
			final String[] instanceState,
			@QueryParam("exitStatus")
			final String exitStatus,
			@QueryParam("page")
			@DefaultValue(value = "0")
			final Integer page,
			@QueryParam("pageSize")
			@DefaultValue(value = "50")
			final Integer pageSize,
			@QueryParam("lastUpdatedTime")
			final LocalDate lastUpdatedTime,
			@QueryParam("sort")
			final String[] sort
			);
	
	/**
	 * @param jobInstanceId
	 * @param createTime
	 * @param instanceState
	 * @param exitStatus
	 * @param page
	 * @param pageSize
	 * @param lastUpdatedTime
	 * @param sort
	 * @param submitter
	 * @param appName
	 * @param jobName
	 * @param ignoreCase
	 */
	@GET
	@Path("v4/jobinstances")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstancesV4(
			@QueryParam("jobInstanceId")
			final String[] jobInstanceId,
			@QueryParam("createTime")
			final String createTime,
			@QueryParam("instanceState")
			final String[] instanceState,
			@QueryParam("exitStatus")
			final String exitStatus,
			@QueryParam("page")
			@DefaultValue(value = "0")
			final Integer page,
			@QueryParam("pageSize")
			@DefaultValue(value = "50")
			final Integer pageSize,
			@QueryParam("lastUpdatedTime")
			final String lastUpdatedTime,
			@QueryParam("sort")
			final String[] sort,
			@QueryParam("submitter")
			final String submitter,
			@QueryParam("appName")
			final String appName,
			@QueryParam("jobName")
			final String jobName,
			@QueryParam("ignoreCase")
			final Boolean ignoreCase
			);
	
	/**
	 * @param job_instance_id
	 * @return
	 */
	@GET
	@Path("jobinstances/{job_instance_id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstance(
			@PathParam("job_instance_id")
			final String job_instance_id
			);
	
	/**
	 * @return
	 */
	@POST
	@Path("jobinstances")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response submit();
	
	/**
	 * @param job_instance_id
	 * @param action
	 * @param reusePreviousParams
	 * @return
	 */
	@PUT
	@Path("jobinstances/{job_instance_id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response stop(
			@PathParam("job_instance_id")
			final String job_instance_id,
			@QueryParam("action")
			@DefaultValue("stop")
			final String action,
			@QueryParam("reusePreviousParams")
			@DefaultValue("false")
			final Boolean reusePreviousParams
			);
	
	/**
	 * @param job_instance_id
	 * @param purgeJobStoreOnly
	 * @return
	 */
	@DELETE
	@Path("jobinstances/{job_instance_id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response purge(
			@PathParam("job_instance_id")
			final String job_instance_id,
			@QueryParam("purgeJobStoreOnly")
			@DefaultValue("false")
			final Boolean purgeJobStoreOnly
			);
	
	/**
	 * @param job_instance_id
	 * @param page
	 * @param pageSize
	 * @param purgeJobStoreOnly
	 * @param jobInstanceId
	 * @param createTime
	 * @param instanceState
	 * @param exitStatus
	 */
	@DELETE
	@Path("v2/jobinstances/{job_instance_id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response purgeV2(
			@PathParam("job_instance_id")
			final String job_instance_id,
			@QueryParam("page")
			@DefaultValue("0")
			final Integer page,
			@QueryParam("pageSize")
			@DefaultValue("50")
			final Integer pageSize,
			@QueryParam("purgeJobStoreOnly")
			@DefaultValue("false")
			final Boolean purgeJobStoreOnly,
			@QueryParam("jobInstanceId")
			final String jobInstanceId,
			@QueryParam("createTime")
			final String createTime,
			@QueryParam("instanceState")
			final String[] instanceState,
			@QueryParam("exitStatus")
			final String exitStatus
			);
	
	/**
	 * @param job_execution_id
	 * @return
	 */
	@GET
	@Path("jobexecutions/{job_execution_id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobExecution(
			@PathParam("job_execution_id")
			final String job_execution_id
			);
	
	/**
	 * @param job_execution_id
	 * @return
	 */
	@GET
	@Path("jobexecutions/{job_execution_id}/jobinstance")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobExecutionInstance(
			@PathParam("job_execution_id")
			final String job_execution_id
			);
	
	/**
	 * @param job_instance_id
	 * @return
	 */
	@GET
	@Path("jobinstances/{job_instance_id}/jobexecutions")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobExecutions(
			@PathParam("job_instance_id")
			final String job_instance_id
			);
	
	/**
	 * @param job_instance_id
	 * @param job_execution_sequence_number
	 * @return
	 */
	@GET
	@Path("jobinstances/{job_instance_id}/jobexecutions/{job_execution_sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getJobInstanceExecutions(
			@PathParam("job_instance_id")
			final String job_instance_id,
			@PathParam("job_execution_sequence_number")
			final String job_execution_sequence_number
			);
}
