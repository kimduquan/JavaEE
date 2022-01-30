package azure.devops.services.test;

public class AttachmentType {
	/**
	 * Attachment type CodeCoverage.
	 */
	String codeCoverage;
	/**
	 * Attachment type ConsoleLog.
	 */
	String consoleLog;
	/**
	 * Attachment type GeneralAttachment , use this as default type unless you have other type.
	 */
	String generalAttachment;
	
	public String getCodeCoverage() {
		return codeCoverage;
	}
	public void setCodeCoverage(String codeCoverage) {
		this.codeCoverage = codeCoverage;
	}
	public String getConsoleLog() {
		return consoleLog;
	}
	public void setConsoleLog(String consoleLog) {
		this.consoleLog = consoleLog;
	}
	public String getGeneralAttachment() {
		return generalAttachment;
	}
	public void setGeneralAttachment(String generalAttachment) {
		this.generalAttachment = generalAttachment;
	}
}
