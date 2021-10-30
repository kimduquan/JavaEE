package azure.devops.services.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface TestSuites {

	/**
	 * Add test cases to suite.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan that contains the suite.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite to which the test cases must be added.
	 * @param testCaseIds IDs of the test cases to add to the suite. Ids are specified in comma separated format.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/Plans/{planId}/suites/{suiteId}/testcases/{testCaseIds}")
	SuiteTestCase[] Add(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@PathParam("testCaseIds")
			String testCaseIds,
			@QueryParam("api-version")
			String api_version
			);
	
	/**
	 * Get a specific test case in a test suite with test case id.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan that contains the suites.
	 * @param project Project ID or project name
	 * @param suiteId ID of the suite that contains the test case.
	 * @param testCaseIds ID of the test case to get.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Plans/{planId}/suites/{suiteId}/testcases/{testCaseIds}")
	@Produces(MediaType.APPLICATION_JSON)
	SuiteTestCase Get(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@PathParam("testCaseIds")
			int testCaseIds,
			@QueryParam("api-version")
			String api_version
			);
	
	/**
	 * Get all test cases in a suite.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan that contains the suites.
	 * @param project Project ID or project name
	 * @param suiteId ID of the suite to get.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Plans/{planId}/suites/{suiteId}/testcases")
	@Produces(MediaType.APPLICATION_JSON)
	SuiteTestCase[] List(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version
			);
	
	/**
	 * The test points associated with the test cases are removed from the test suite. 
	 * The test case work item is not deleted from the system. 
	 * See test cases resource to delete a test case permanently.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan that contains the suite.
	 * @param project Project ID or project name
	 * @param suiteId ID of the suite to get.
	 * @param testCaseIds IDs of the test cases to remove from the suite.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/test/Plans/{planId}/suites/{suiteId}/testcases/{testCaseIds}")
	void RemoveTestCasesFromSuiteUrl(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@PathParam("testCaseIds")
			String testCaseIds,
			@QueryParam("api-version")
			String api_version
			);
	
	public class Update_RequestBody {
		/**
		 * Shallow reference of configurations for the test cases in the suite.
		 */
		ShallowReference[] configurations;

		public ShallowReference[] getConfigurations() {
			return configurations;
		}

		public void setConfigurations(ShallowReference[] configurations) {
			this.configurations = configurations;
		}
	}
	
	/**
	 * Updates the properties of the test case association in a suite.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan that contains the suite.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite to which the test cases must be added.
	 * @param testCaseIds IDs of the test cases to add to the suite. Ids are specified in comma separated format.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/_apis/test/Plans/{planId}/suites/{suiteId}/testcases/{testCaseIds}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SuiteTestCase[] Update(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@PathParam("testCaseIds")
			String testCaseIds,
			@QueryParam("api-version")
			String api_version,
			Update_RequestBody body
			);
}
