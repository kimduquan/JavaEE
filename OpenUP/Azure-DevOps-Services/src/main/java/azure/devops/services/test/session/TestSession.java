package azure.devops.services.test.session;

import azure.devops.services.test.IdentityRef;
import azure.devops.services.test.ShallowReference;

/**
 * @author PC
 * Test Session
 */
public class TestSession {

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
