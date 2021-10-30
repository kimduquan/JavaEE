package azure.devops.services.test.session;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import azure.devops.services.test.IdentityRef;
import azure.devops.services.test.ShallowReference;

public interface Session {
	
	public class Create_RequestBody {
		
		/**
		 * Area path of the test session
		 */
		ShallowReference area;
		/**
		 * Comments in the test session
		 */
		String comment;
		/**
		 * Duration of the session
		 */
		String endDate;
		/**
		 * Id of the test session
		 */
		int id;
		/**
		 * Last Updated By Reference
		 */
		IdentityRef lastUpdatedBy;
		/**
		 * Last updated date
		 */
		String lastUpdatedDate;
		/**
		 * Owner of the test session
		 */
		IdentityRef owner;
		/**
		 * Project to which the test session belongs
		 */
		ShallowReference project;
		/**
		 * Generic store for test session data
		 */
		PropertyBag propertyBag;
		/**
		 * Revision of the test session
		 */
		int revision;
		/**
		 * Source of the test session
		 */
		TestSessionSource source;
		/**
		 * Start date
		 */
		String startDate;
		/**
		 * State of the test session
		 */
		TestSessionState state;
		/**
		 * Title of the test session
		 */
		String title;
		/**
		 * Url of Test Session Resource
		 */
		String url;
		
		public ShallowReference getArea() {
			return area;
		}
		public void setArea(ShallowReference area) {
			this.area = area;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public IdentityRef getLastUpdatedBy() {
			return lastUpdatedBy;
		}
		public void setLastUpdatedBy(IdentityRef lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}
		public String getLastUpdatedDate() {
			return lastUpdatedDate;
		}
		public void setLastUpdatedDate(String lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
		public IdentityRef getOwner() {
			return owner;
		}
		public void setOwner(IdentityRef owner) {
			this.owner = owner;
		}
		public ShallowReference getProject() {
			return project;
		}
		public void setProject(ShallowReference project) {
			this.project = project;
		}
		public PropertyBag getPropertyBag() {
			return propertyBag;
		}
		public void setPropertyBag(PropertyBag propertyBag) {
			this.propertyBag = propertyBag;
		}
		public int getRevision() {
			return revision;
		}
		public void setRevision(int revision) {
			this.revision = revision;
		}
		public TestSessionSource getSource() {
			return source;
		}
		public void setSource(TestSessionSource source) {
			this.source = source;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public TestSessionState getState() {
			return state;
		}
		public void setState(TestSessionState state) {
			this.state = state;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

	/**
	 * Create a test session
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param team Team ID or team name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @return
	 */
	@POST
	@Path("{organization}/{project}/{team}/_apis/test/session")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestSession Create(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("team")
			String team,
			@QueryParam("api-version")
			String api_version,
			Create_RequestBody body);
	
	/**
	 * Get a list of test sessions
	 * @param organization The name of the Azure DevOps organization.
	 * @param project Project ID or project name
	 * @param team Team ID or team name
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param allSessions If false, returns test sessions for current user. Otherwise, it returns test sessions for all users
	 * @param includeAllProperties If true, it returns all properties of the test sessions. Otherwise, it returns the skinny version.
	 * @param includeOnlyCompletedSessions If true, it returns test sessions in completed state. Otherwise, it returns test sessions for all states
	 * @param period Period in days from now, for which test sessions are fetched.
	 * @param source Source of the test session.
	 * @return
	 */
	@GET
	@Path("{organization}/{project}/{team}/_apis/test/session")
	@Produces(MediaType.APPLICATION_JSON)
	TestSession[] List(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("team")
			String team,
			@QueryParam("api-version")
			String api_version,
			@QueryParam("allSessions")
			boolean allSessions,
			@QueryParam("includeAllProperties")
			boolean includeAllProperties,
			@QueryParam("includeOnlyCompletedSessions")
			boolean includeOnlyCompletedSessions,
			@QueryParam("period")
			int period,
			@QueryParam("source")
			TestSessionSource source
			);
	
	public class Update_RequestBody {
		/**
		 * Area path of the test session
		 */
		ShallowReference area;
		/**
		 * Comments in the test session
		 */
		String comment;
		/**
		 * Duration of the session
		 */
		String endDate;
		/**
		 * Id of the test session
		 */
		int id;
		/**
		 * Last Updated By Reference
		 */
		IdentityRef lastUpdatedBy;
		/**
		 * Last updated date
		 */
		String lastUpdatedDate;
		/**
		 * Owner of the test session
		 */
		IdentityRef owner;
		/**
		 * Project to which the test session belongs
		 */
		ShallowReference project;
		/**
		 * Generic store for test session data
		 */
		PropertyBag propertyBag;
		/**
		 * Revision of the test session
		 */
		int revision;
		/**
		 * Source of the test session
		 */
		TestSessionSource source;
		/**
		 * Start date
		 */
		String startDate;
		/**
		 * State of the test session
		 */
		TestSessionState state;
		/**
		 * Title of the test session
		 */
		String title;
		/**
		 * Url of Test Session Resource
		 */
		String url;
		
		public ShallowReference getArea() {
			return area;
		}
		public void setArea(ShallowReference area) {
			this.area = area;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public IdentityRef getLastUpdatedBy() {
			return lastUpdatedBy;
		}
		public void setLastUpdatedBy(IdentityRef lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}
		public String getLastUpdatedDate() {
			return lastUpdatedDate;
		}
		public void setLastUpdatedDate(String lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
		public IdentityRef getOwner() {
			return owner;
		}
		public void setOwner(IdentityRef owner) {
			this.owner = owner;
		}
		public ShallowReference getProject() {
			return project;
		}
		public void setProject(ShallowReference project) {
			this.project = project;
		}
		public PropertyBag getPropertyBag() {
			return propertyBag;
		}
		public void setPropertyBag(PropertyBag propertyBag) {
			this.propertyBag = propertyBag;
		}
		public int getRevision() {
			return revision;
		}
		public void setRevision(int revision) {
			this.revision = revision;
		}
		public TestSessionSource getSource() {
			return source;
		}
		public void setSource(TestSessionSource source) {
			this.source = source;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public TestSessionState getState() {
			return state;
		}
		public void setState(TestSessionState state) {
			this.state = state;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	
	/**
	 * Update a test session
	 * @param organization
	 * @param project
	 * @param team
	 * @param api_version
	 * @param body
	 * @return
	 */
	@PATCH
	@Path("{organization}/{project}/{team}/_apis/test/session")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	TestSession Update(
			@PathParam("organization")
			String organization,
			@PathParam("project")
			String project,
			@PathParam("team")
			String team,
			@QueryParam("api-version")
			String api_version,
			Update_RequestBody body
			);
}
