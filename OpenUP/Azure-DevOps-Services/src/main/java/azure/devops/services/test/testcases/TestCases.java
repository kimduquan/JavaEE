package azure.devops.services.test.testcases;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface TestCases {

	/**
	 * Delete a test case.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param testCaseId Id of test case to delete.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/test/testcases/{testCaseId}")
	void Delete(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("testCaseId")
			int testCaseId,
			@QueryParam("api-version")
			String api_version
			);
}
