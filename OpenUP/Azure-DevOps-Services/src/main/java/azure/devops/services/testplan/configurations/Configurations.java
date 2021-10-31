package azure.devops.services.testplan.configurations;

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

public interface Configurations {

	/**
	 * Create a test configuration.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/testplan/configurations")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestConfiguration Create(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			TestConfigurationCreateUpdateParameters body);
	
	/**
	 * Delete a test configuration by its ID.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param testConfiguartionId ID of the test configuration to delete.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/testplan/configurations")
	void Delete(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("testConfiguartionId")
			int testConfiguartionId
			);
	
	/**
	 * Get a test configuration
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param testConfigurationId ID of the test configuration to get.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/testplan/configurations/{testConfigurationId}")
	@Produces(MediaType.APPLICATION_JSON)
	TestConfiguration Get(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("testConfigurationId")
			int testConfigurationId,
			@QueryParam("api-version")
			String api_version
			);
	
	/**
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param continuationToken If the list of configurations returned is not complete, a continuation token to query next batch of configurations is included in the response header as "x-ms-continuationtoken". 
	 * Omit this parameter to get the first batch of test configurations.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/testplan/configurations")
	@Produces(MediaType.APPLICATION_JSON)
	TestConfiguration[] List(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("continuationToken")
			String continuationToken
			);
	
	/**
	 * Update a test configuration by its ID.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param testConfiguartionId ID of the test configuration to update.
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/_apis/testplan/configurations")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TestConfiguration Update(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("testConfiguartionId")
			int testConfiguartionId,
			TestConfigurationCreateUpdateParameters body
			);
}
