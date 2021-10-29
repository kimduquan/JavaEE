package azure.devops.services.test;

public class TestAttachment {
	/**
	 * Attachment type.
	 */
	AttachmentType attachmentType;
	/**
	 * Comment associated with attachment.
	 */
	String comment;
	/**
	 * Attachment created date.
	 */
	String createdDate;
	/**
	 * Attachment file name
	 */
	String fileName;
	/**
	 * ID of the attachment.
	 */
	int id;
	/**
	 * Attachment size.
	 */
	int size;
	/**
	 * Attachment Url.
	 */
	String url;
	
	public AttachmentType getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(AttachmentType attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
