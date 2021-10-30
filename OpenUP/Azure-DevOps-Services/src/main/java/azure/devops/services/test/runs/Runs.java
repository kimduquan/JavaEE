package azure.devops.services.test.runs;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import azure.devops.services.test.CustomTestField;
import azure.devops.services.test.IdentityRef;
import azure.devops.services.test.ReleaseReference;
import azure.devops.services.test.ShallowReference;

public interface Runs {
	
	class RequestBody {
		/**
		 * true if test run is automated, false otherwise. 
		 * By default it will be false.
		 */
		boolean automated;
		/**
		 * An abstracted reference to the build that it belongs.
		 */
		ShallowReference build;
		/**
		 * Drop location of the build used for test run.
		 */
		String buildDropLocation;
		/**
		 * Flavor of the build used for test run. 
		 * (E.g: Release, Debug)
		 */
		String buildFlavor;
		/**
		 * Platform of the build used for test run. 
		 * (E.g.: x86, amd64)
		 */
		String buildPlatform;
		/**
		 * BuildReference of the test run.
		 */
		BuildConfiguration buildReference;
		/**
		 * Comments entered by those analyzing the run.
		 */
		String comment;
		/**
		 * Completed date time of the run.
		 */
		String completeDate;
		/**
		 * IDs of the test configurations associated with the run.
		 */
		int[] configurationIds;
		/**
		 * Name of the test controller used for automated run.
		 */
		String controller;
		/**
		 * Additional properties of test Run.
		 */
		CustomTestField[] customTestFields;
		/**
		 * An abstracted reference to DtlAutEnvironment.
		 */
		ShallowReference dtlAutEnvironment;
		/**
		 * An abstracted reference to DtlTestEnvironment.
		 */
		ShallowReference dtlTestEnvironment;
		/**
		 * Due date and time for test run.
		 */
		String dueDate;
		/**
		 * This is a temporary class to provide the details for the test run environment.
		 */
		DtlEnvironmentDetails dtlEnvironmentCreationDetails;
		/**
		 * Error message associated with the run.
		 */
		String errorMessage;
		/**
		 * Filter used for discovering the Run.
		 */
		RunFilter filter;
		/**
		 * The iteration in which to create the run. 
		 * Root iteration of the team project will be default
		 */
		String iteration;
		/**
		 * Name of the test run.
		 */
		String name;
		/**
		 * Display name of the owner of the run.
		 */
		IdentityRef owner;
		/**
		 * Reference of the pipeline to which this test run belongs. 
		 * PipelineReference.PipelineId should be equal to RunCreateModel.Build.Id
		 */
		PipelineReference pipelineReference;
		/**
		 * An abstracted reference to the plan that it belongs.
		 */
		ShallowReference plan;
		/**
		 * IDs of the test points to use in the run.
		 */
		int[] pointIds;
		/**
		 * URI of release environment associated with the run.
		 */
		String releaseEnvironmentUri;
		/**
		 * Reference to release associated with test run.
		 */
		ReleaseReference releaseReference;
		/**
		 * URI of release associated with the run.
		 */
		String releaseUri;
		/**
		 * Run summary for run Type = NoConfigRun.
		 */
		RunSummaryModel[] runSummary;
		/**
		 * Timespan till the run times out.
		 */
		String runTimeout;
		/**
		 * SourceWorkFlow(CI/CD) of the test run.
		 */
		String sourceWorkflow;
		/**
		 * Start date time of the run.
		 */
		String startDate;
		/**
		 * The state of the run. 
		 * Type TestRunState Valid states - NotStarted, InProgress, Waiting
		 */
		String state;
		/**
		 * Tags to attach with the test run, maximum of 5 tags can be added to run.
		 */
		TestTag[] tags;
		/**
		 * TestConfigurationMapping of the test run.
		 */
		String testConfigurationsMapping;
		/**
		 * ID of the test environment associated with the run.
		 */
		String testEnvironmentId;
		/**
		 * An abstracted reference to the test settings resource.
		 */
		ShallowReference testSettings;
		/**
		 * Type of the run(RunType) Valid Values : (Unspecified, Normal, Blocking, Web, MtrRunInitiatedFromWeb, RunWithDtlEnv, NoConfigRun)
		 */
		String type;
		
		public boolean isAutomated() {
			return automated;
		}
		public void setAutomated(boolean automated) {
			this.automated = automated;
		}
		public ShallowReference getBuild() {
			return build;
		}
		public void setBuild(ShallowReference build) {
			this.build = build;
		}
		public String getBuildDropLocation() {
			return buildDropLocation;
		}
		public void setBuildDropLocation(String buildDropLocation) {
			this.buildDropLocation = buildDropLocation;
		}
		public String getBuildFlavor() {
			return buildFlavor;
		}
		public void setBuildFlavor(String buildFlavor) {
			this.buildFlavor = buildFlavor;
		}
		public String getBuildPlatform() {
			return buildPlatform;
		}
		public void setBuildPlatform(String buildPlatform) {
			this.buildPlatform = buildPlatform;
		}
		public BuildConfiguration getBuildReference() {
			return buildReference;
		}
		public void setBuildReference(BuildConfiguration buildReference) {
			this.buildReference = buildReference;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getCompleteDate() {
			return completeDate;
		}
		public void setCompleteDate(String completeDate) {
			this.completeDate = completeDate;
		}
		public int[] getConfigurationIds() {
			return configurationIds;
		}
		public void setConfigurationIds(int[] configurationIds) {
			this.configurationIds = configurationIds;
		}
		public String getController() {
			return controller;
		}
		public void setController(String controller) {
			this.controller = controller;
		}
		public CustomTestField[] getCustomTestFields() {
			return customTestFields;
		}
		public void setCustomTestFields(CustomTestField[] customTestFields) {
			this.customTestFields = customTestFields;
		}
		public ShallowReference getDtlAutEnvironment() {
			return dtlAutEnvironment;
		}
		public void setDtlAutEnvironment(ShallowReference dtlAutEnvironment) {
			this.dtlAutEnvironment = dtlAutEnvironment;
		}
		public ShallowReference getDtlTestEnvironment() {
			return dtlTestEnvironment;
		}
		public void setDtlTestEnvironment(ShallowReference dtlTestEnvironment) {
			this.dtlTestEnvironment = dtlTestEnvironment;
		}
		public String getDueDate() {
			return dueDate;
		}
		public void setDueDate(String dueDate) {
			this.dueDate = dueDate;
		}
		public DtlEnvironmentDetails getDtlEnvironmentCreationDetails() {
			return dtlEnvironmentCreationDetails;
		}
		public void setDtlEnvironmentCreationDetails(DtlEnvironmentDetails dtlEnvironmentCreationDetails) {
			this.dtlEnvironmentCreationDetails = dtlEnvironmentCreationDetails;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public RunFilter getFilter() {
			return filter;
		}
		public void setFilter(RunFilter filter) {
			this.filter = filter;
		}
		public String getIteration() {
			return iteration;
		}
		public void setIteration(String iteration) {
			this.iteration = iteration;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public IdentityRef getOwner() {
			return owner;
		}
		public void setOwner(IdentityRef owner) {
			this.owner = owner;
		}
		public PipelineReference getPipelineReference() {
			return pipelineReference;
		}
		public void setPipelineReference(PipelineReference pipelineReference) {
			this.pipelineReference = pipelineReference;
		}
		public ShallowReference getPlan() {
			return plan;
		}
		public void setPlan(ShallowReference plan) {
			this.plan = plan;
		}
		public int[] getPointIds() {
			return pointIds;
		}
		public void setPointIds(int[] pointIds) {
			this.pointIds = pointIds;
		}
		public String getReleaseEnvironmentUri() {
			return releaseEnvironmentUri;
		}
		public void setReleaseEnvironmentUri(String releaseEnvironmentUri) {
			this.releaseEnvironmentUri = releaseEnvironmentUri;
		}
		public ReleaseReference getReleaseReference() {
			return releaseReference;
		}
		public void setReleaseReference(ReleaseReference releaseReference) {
			this.releaseReference = releaseReference;
		}
		public String getReleaseUri() {
			return releaseUri;
		}
		public void setReleaseUri(String releaseUri) {
			this.releaseUri = releaseUri;
		}
		public RunSummaryModel[] getRunSummary() {
			return runSummary;
		}
		public void setRunSummary(RunSummaryModel[] runSummary) {
			this.runSummary = runSummary;
		}
		public String getRunTimeout() {
			return runTimeout;
		}
		public void setRunTimeout(String runTimeout) {
			this.runTimeout = runTimeout;
		}
		public String getSourceWorkflow() {
			return sourceWorkflow;
		}
		public void setSourceWorkflow(String sourceWorkflow) {
			this.sourceWorkflow = sourceWorkflow;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public TestTag[] getTags() {
			return tags;
		}
		public void setTags(TestTag[] tags) {
			this.tags = tags;
		}
		public String getTestConfigurationsMapping() {
			return testConfigurationsMapping;
		}
		public void setTestConfigurationsMapping(String testConfigurationsMapping) {
			this.testConfigurationsMapping = testConfigurationsMapping;
		}
		public String getTestEnvironmentId() {
			return testEnvironmentId;
		}
		public void setTestEnvironmentId(String testEnvironmentId) {
			this.testEnvironmentId = testEnvironmentId;
		}
		public ShallowReference getTestSettings() {
			return testSettings;
		}
		public void setTestSettings(ShallowReference testSettings) {
			this.testSettings = testSettings;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	/**
	 * Create new test run.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/_apis/test/runs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestRun Create(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@QueryParam("api-version")
			String api_version,
			RequestBody body);
	
	/**
	 * Delete a test run by its ID.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the run to delete.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 */
	@DELETE
	@Path("{organization}/{project}/_apis/test/runs/{runId}")
	void Delete(
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
	 * Get a test run by its ID.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the run to get.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param includeDetails Default value is true. It includes details like run statistics, release, build, test environment, post process state, and more.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/runs/{runId}")
	@Produces(MediaType.APPLICATION_JSON)
	TestRun GetTestRunByIḍ̣(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("runId")
			int runId,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("includeDetails")
			boolean includeDetails
			);
	
	/**
	 * Get test run statistics , used when we want to get summary of a run by outcome.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param runId ID of the run to get.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/runs/{runId}/Statistics")
	@Produces(MediaType.APPLICATION_JSON)
	TestRunStatistic GetTestRunStatistics(
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
	 * Get a list of test runs.
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.3' to use this version of the api.
	 * @param skip Number of test runs to skip.
	 * @param top Number of test runs to return.
	 * @param automated If true, only returns automated runs.
	 * @param buildUri URI of the build that the runs used.
	 * @param includeRunDetails If true, include all the properties of the runs.
	 * @param owner Team foundation ID of the owner of the runs.
	 * @param planId ID of the test plan that the runs are a part of.
	 * @param tmiRunId
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/_apis/test/runs")
	@Produces(MediaType.APPLICATION_JSON)
	TestRun[] List(
			String organization,
			String project,
			String api_version,
			int skip,
			int top,
			boolean automated,
			String buildUri,
			boolean includeRunDetails,
			String owner,
			int planId,
			String tmiRunId);
}
