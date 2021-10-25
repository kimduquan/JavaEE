package azure.devops.services.pipelines.approvals;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
public interface Approvals {

	/**
	 * Get an approval.
	 * @param approvalId Id of the approval.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param _expand
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/pipelines/approvals/{approvalId}")
	@Produces(MediaType.APPLICATION_JSON)
	Approval Get(
			@PathParam("approvalId") 
			final String approvalId,
			@PathParam("organization")
			final String organization,
			@PathParam("project")
			final String project,
			@QueryParam("api-version")
			final String api_version,
			@QueryParam("_expand")
			final ApprovalDetailsExpandParameter _expand);
	
	/**
	 * List Approvals. 
	 * This can be used to get a set of pending approvals in a pipeline, on an user or for a resource..
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param _expand
	 * @param approvalIds
	 */
	@Path("{organization}/{project}/_apis/pipelines/approvals")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Approval[] Query(
			@PathParam("organization") 
			final String organization,
			@PathParam("project")
			final String project,
			@QueryParam("api-version")
			final String api_version,
			@QueryParam("_expand")
			final ApprovalDetailsExpandParameter _expand,
			@QueryParam("approvalIds")
			final String[] approvalIds);
	
	/**
	 * Update approvals.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param body
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/_apis/pipelines/approvals")
	@Produces(MediaType.APPLICATION_JSON)
	Approval[] Update(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			ApprovalUpdateParameters[] body
			);
}
