package azure.devops.services.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public interface Attachments {

	/**
	 * Attach a file to a test result.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the result.
	 * @param testCaseResultId ID of the test result against which attachment has to be uploaded.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachmentReference CreateTestResultAttachment(
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
			TestAttachmentRequestModel requestBody);
	
	/**
	 * Attach a file to a test run.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run against which attachment has to be uploaded.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachmentReference CreateTestRunAttachment(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version,
			TestAttachmentRequestModel requestBody);
	
	/**
	 * Attach a file to a test result
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the result.
	 * @param testCaseResultId ID of the test results that contains sub result.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param testSubResultId ID of the test sub results against which attachment has to be uploaded.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachmentReference CreateTestSubResultAttachment(
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
			@QueryParam("testSubResultId")
			int testSubResultId,
			TestAttachmentRequestModel requestBody);
	
	/**
	 * Download a test result attachment by its ID.
	 * @param attachmentId ID of the test result attachment to be downloaded.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the testCaseResultId.
	 * @param testCaseResultId ID of the test result whose attachment has to be downloaded.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments/{attachmentId}")
	@Produces({MediaType.APPLICATION_OCTET_STREAM, "application/zip"})
	String GetTestResultAttachmentZip(
			@PathParam("attachmentId")
			String attachmentId,
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@PathParam("testCaseResultId")
			int testCaseResultId,
			@QueryParam("api-version")
			String api_version);
	
	/**
	 * Get list of test result attachments reference.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the result.
	 * @param testCaseResultId ID of the test result.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments")
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachment[] GetTestResultAttachments(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@PathParam("testCaseResultId")
			int testCaseResultId,
			@QueryParam("api-version")
			String api_version);
	
	/**
	 * Download a test run attachment by its ID.
	 * @param attachmentId ID of the test run attachment to be downloaded.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run whose attachment has to be downloaded.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/attachments/{attachmentId}")
	@Produces({MediaType.APPLICATION_OCTET_STREAM, "application/zip"})
	String GetTestRunAttachmentZip(
			@PathParam("attachmentId")
			String attachmentId,
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version);
	
	/**
	 * Get list of test run attachments reference.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/attachments")
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachment[] GetTestRunAttachments(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version
			);
	
	/**
	 * Download a test sub result attachment
	 * @param attachmentId ID of the test result attachment to be downloaded
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the result.
	 * @param testCaseResultId ID of the test results that contains sub result.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param testSubResultId ID of the test sub result whose attachment has to be downloaded
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments/{attachmentId}")
	@Produces({MediaType.APPLICATION_OCTET_STREAM, "application/zip"})
	String GetTestSubResultAttachmentZip(
			@PathParam("attachmentId")
			int attachmentId,
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
			@QueryParam("testSubResultId")
			int testSubResultId);
	
	/**
	 * Get list of test sub result attachments
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the test run that contains the result.
	 * @param testCaseResultId ID of the test results that contains sub result.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param testSubResultId ID of the test sub result whose attachment has to be downloaded
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/Runs/{runId}/Results/{testCaseResultId}/attachments")
	@Produces(MediaType.APPLICATION_JSON)
	TestAttachment[] GetTestSubResultAttachments(
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
			@QueryParam("testSubResultId")
			int testSubResultId);
}
