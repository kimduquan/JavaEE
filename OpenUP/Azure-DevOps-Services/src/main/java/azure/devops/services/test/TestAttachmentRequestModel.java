package azure.devops.services.test;

/**
 * @author PC
 * Test attachment request model
 */
public class TestAttachmentRequestModel {
	/**
	 * Attachment type By Default it will be GeneralAttachment. 
	 * It can be one of the following type. { 
	 * GeneralAttachment, 
	 * AfnStrip, 
	 * BugFilingData, 
	 * CodeCoverage, 
	 * IntermediateCollectorData, 
	 * RunConfig, 
	 * TestImpactDetails, 
	 * TmiTestRunDeploymentFiles, 
	 * TmiTestRunReverseDeploymentFiles, 
	 * TmiTestResultDetail, 
	 * TmiTestRunSummary }
	 */
	String attachmentType;
	/**
	 * Comment associated with attachment
	 */
	String comment;
	/**
	 * Attachment filename
	 */
	String fileName;
	/**
	 * Base64 encoded file stream
	 */
	String stream;
	
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
}