package azure.devops.services.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface Results {

	/**
	 * Add test results to a test run.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId Test run ID into which test results to add.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.6' to use this version of the api.
	 * @param body
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/results")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestCaseResult[] Add(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version,
			TestCaseResult[] body);
	
	/**
	 * Get a test result for a test run.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId Test run ID of a test result to fetch.
	 * @param testCaseResultId Test result ID.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.6' to use this version of the api.
	 * @param detailsToInclude Details to include with test results. Default is None. Other values are Iterations, WorkItems and SubResults.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/results/{testCaseResultId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestCaseResult Get(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@PathParam("testCaseResultId")
			int testCaseResultId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("detailsToInclude")
			ResultDetails detailsToInclude);
	
	/**
	 * Get test results for a test run.
	 * @param organization
	 * @param project
	 * @param runId
	 * @param api_version
	 * @param skip
	 * @param top
	 * @param detailsToInclude
	 * @param outcomes
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/results")
	@Produces(MediaType.APPLICATION_JSON)
	TestCaseResult[] List(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("skip")
			int skip,
			@QueryParam("top")
			int top,
			@QueryParam("detailsToInclude")
			ResultDetails detailsToInclude,
			@QueryParam("outcomes")
			String[] outcomes);
	
	/**
	 * Update test results in a test run.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId Test run ID whose test results to update.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.6' to use this version of the api.
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/results")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestCaseResult[] Update(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version,
			TestCaseResult[] body);
}
