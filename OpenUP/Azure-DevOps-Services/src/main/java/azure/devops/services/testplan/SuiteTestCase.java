package azure.devops.services.testplan;

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

public interface SuiteTestCase {

	/**
	 * Add test cases to a suite with specified configurations
	 * @param organization
	 * @param planId
	 * @param project
	 * @param suiteId
	 * @param api_version
	 */
	@POST
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestCase[] Add(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version,
			SuiteTestCaseCreateUpdateParameters[] body
			);
	
	/**
	 * Get a particular Test Case from a Suite.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan for which test cases are requested.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite for which test cases are requested.
	 * @param testCaseId Test Case Id to be fetched.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param returnIdentityRef If set to true, returns all identity fields, like AssignedTo, ActivatedBy etc., as IdentityRef objects. 
	 * If set to false, these fields are returned as unique names in string format. 
	 * This is false by default.
	 * @param witFields Get the list of witFields.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase/{testCaseId}")
	@Produces(MediaType.APPLICATION_JSON)
	TestCase[] GetTestCase(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@PathParam("testCaseId")
			String testCaseId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("returnIdentityRef")
			boolean returnIdentityRef,
			@QueryParam("witFields")
			String witFields
			);
	
	/**
	 * Get Test Case List return those test cases which have all the configuration Ids as mentioned in the optional parameter. 
	 * If configuration Ids is null, it return all the test cases
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan for which test cases are requested.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite for which test cases are requested.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param configurationIds Fetch Test Cases which contains all the configuration Ids specified.
	 * @param continuationToken If the list of test cases returned is not complete, a continuation token to query next batch of test cases is included in the response header as "x-ms-continuationtoken". 
	 * Omit this parameter to get the first batch of test cases.
	 * @param excludeFlags Flag to exclude various values from payload. 
	 * For example to remove point assignments pass exclude = 1. 
	 * To remove extra information (links, test plan , test suite) pass exclude = 2. 
	 * To remove both extra information and point assignments pass exclude = 3 (1 + 2).
	 * @param expand If set to false, will get a smaller payload containing only basic details about the suite test case object
	 * @param isRecursive
	 * @param returnIdentityRef If set to true, returns all identity fields, like AssignedTo, ActivatedBy etc., as IdentityRef objects. 
	 * If set to false, these fields are returned as unique names in string format. 
	 * This is false by default.
	 * @param testIds Test Case Ids to be fetched.
	 * @param witFields Get the list of witFields.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase")
	@Produces(MediaType.APPLICATION_JSON)
	TestCase[] GetTestCaseList(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("configurationIds")
			String configurationIds,
			@QueryParam("continuationToken")
			String continuationToken,
			@QueryParam("excludeFlags")
			ExcludeFlags excludeFlags,
			@QueryParam("expand")
			boolean expand,
			@QueryParam("isRecursive")
			boolean isRecursive,
			@QueryParam("returnIdentityRef")
			boolean returnIdentityRef,
			@QueryParam("testIds")
			String testIds,
			@QueryParam("witFields")
			String witFields
			);
	
	/**
	 * Removes test cases from a suite based on the list of test case Ids provided.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan from which test cases are to be removed.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite from which test cases are to be removed.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param testCaseIds Test Case Ids to be removed.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase")
	void RemoveTestCasesFromSuite(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("testCaseIds")
			String testCaseIds
			);
	
	/**
	 * Removes test cases from a suite based on the list of test case Ids provided. 
	 * This API can be used to remove a larger number of test cases.
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan from which test cases are to be removed.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite from which test cases are to be removed.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param testIds Comma separated string of Test Case Ids to be removed.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase")
	void RemoveTestCasesListFromSuite(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("testIds")
			String testIds
			);
	
	/**
	 * Update the configurations for test cases
	 * @param organization The name of the Azure DevOps organization.
	 * @param planId ID of the test plan to which test cases are to be updated.
	 * @param project Project ID or project name
	 * @param suiteId ID of the test suite to which test cases are to be updated.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/_apis/testplan/Plans/{planId}/Suites/{suiteId}/TestCase")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestCase[] Update(
			@PathParam("organization")
			String organization,
			@PathParam("planId")
			int planId,
			@PathParam("project")
			String project,
			@PathParam("suiteId")
			int suiteId,
			@QueryParam("api-version")
			String api_version,
			SuiteTestCaseCreateUpdateParameters[] body
			);
}
