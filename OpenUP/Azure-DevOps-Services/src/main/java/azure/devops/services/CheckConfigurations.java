package azure.devops.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
public interface CheckConfigurations {
	
	/**
	 * @author PC
	 *
	 */
	public class RequestBody {
		/**
		 * Identity of person who configured check.
		 */
		private IdentityRef createdBy;
		/**
		 * Time when check got configured.
		 */
		private String createdOn;
		/**
		 * Check configuration id.
		 */
		private int id;
		/**
		 * Identity of person who modified the configured check.
		 */
		private IdentityRef modifiedBy;
		/**
		 * Time when configured check was modified.
		 */
		private String modifiedOn;
		/**
		 * Resource on which check get configured.
		 */
		private Resource resource;
		/**
		 * Timeout in minutes for the check.
		 */
		private int timeout;
		/**
		 * Check configuration type
		 */
		private CheckType type;
		/**
		 * The URL from which one can fetch the configured check.
		 */
		private String url;
		
		public IdentityRef getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(IdentityRef createdBy) {
			this.createdBy = createdBy;
		}
		public String getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(String createdOn) {
			this.createdOn = createdOn;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public IdentityRef getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(IdentityRef modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public String getModifiedOn() {
			return modifiedOn;
		}
		public void setModifiedOn(String modifiedOn) {
			this.modifiedOn = modifiedOn;
		}
		public Resource getResource() {
			return resource;
		}
		public void setResource(Resource resource) {
			this.resource = resource;
		}
		public int getTimeout() {
			return timeout;
		}
		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}
		public CheckType getType() {
			return type;
		}
		public void setType(CheckType type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

	/**
	 * Add a check configuration
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param body
	 */
	@POST
	@Path("{organization}/{project}/_apis/pipelines/checks/configurations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	void Add(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			RequestBody body);
	
	/**
	 * Delete check configuration by id
	 * @param id check configuration id
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/pipelines/checks/configurations/{id}")
	void Delete(
			@PathParam("id")
			int id,
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version);
	
	/**
	 * Get Check configuration by Id
	 * @param id
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param _expand
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/pipelines/checks/configurations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	CheckConfiguration Get(
			@PathParam("id")
			int id,
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("_expand")
			CheckConfigurationExpandParameter _expand
			);
	
	/**
	 * Get Check configuration by resource type and id
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param _expand
	 * @param resourceId resource id
	 * @param resourceType resource type
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/pipelines/checks/configurations")
	@Produces(MediaType.APPLICATION_JSON)
	CheckConfiguration[] List(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("_expand")
			CheckConfigurationExpandParameter _expand,
			@QueryParam("resourceId")
			String resourceId,
			@QueryParam("resourceType")
			String resourceType
			);
}
