package azure.devops.services.test;

import javax.ws.rs.Consumes;
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
}
