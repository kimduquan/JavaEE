package azure.devops.services.testplan.configurations;

import javax.ws.rs.Consumes;
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
}
