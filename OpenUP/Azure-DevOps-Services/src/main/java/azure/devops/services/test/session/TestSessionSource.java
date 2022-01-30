package azure.devops.services.test.session;

/**
 * @author PC
 * Source of the test session
 */
public class TestSessionSource {

	/**
	 * The session was created from feedback client.
	 */
	String feedbackDesktop;
	/**
	 * The session was created from browser extension.
	 */
	String feedbackWeb;
	/**
	 * To show sessions from all supported sources.
	 */
	String sessionInsightsForAll;
	/**
	 * Source of test session uncertain as it is stale
	 */
	String unknown;
	/**
	 * The session was created from Microsoft Test Manager exploratory desktop tool.
	 */
	String xtDesktop;
	/**
	 * The session was created from web access using Microsoft Test Manager exploratory desktop tool.
	 */
	String xtDesktop2;
	/**
	 * The session was created from browser extension.
	 */
	String xtWeb;
	
	public String getFeedbackDesktop() {
		return feedbackDesktop;
	}
	public void setFeedbackDesktop(String feedbackDesktop) {
		this.feedbackDesktop = feedbackDesktop;
	}
	public String getFeedbackWeb() {
		return feedbackWeb;
	}
	public void setFeedbackWeb(String feedbackWeb) {
		this.feedbackWeb = feedbackWeb;
	}
	public String getSessionInsightsForAll() {
		return sessionInsightsForAll;
	}
	public void setSessionInsightsForAll(String sessionInsightsForAll) {
		this.sessionInsightsForAll = sessionInsightsForAll;
	}
	public String getUnknown() {
		return unknown;
	}
	public void setUnknown(String unknown) {
		this.unknown = unknown;
	}
	public String getXtDesktop() {
		return xtDesktop;
	}
	public void setXtDesktop(String xtDesktop) {
		this.xtDesktop = xtDesktop;
	}
	public String getXtDesktop2() {
		return xtDesktop2;
	}
	public void setXtDesktop2(String xtDesktop2) {
		this.xtDesktop2 = xtDesktop2;
	}
	public String getXtWeb() {
		return xtWeb;
	}
	public void setXtWeb(String xtWeb) {
		this.xtWeb = xtWeb;
	}
}
