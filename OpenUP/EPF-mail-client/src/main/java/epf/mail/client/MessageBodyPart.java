package epf.mail.client;

import java.util.List;

/**
 * 
 */
public class MessageBodyPart extends MessagePart {

	/**
	 *
	 */
	private List<AttachFile> attachFiles;

	public List<AttachFile> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(final List<AttachFile> attachFiles) {
		this.attachFiles = attachFiles;
	}
}
