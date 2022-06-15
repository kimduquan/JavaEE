package epf.client.mail;

import java.util.List;

/**
 * 
 */
public class MessageMultipart extends MessagePart {

	/**
	 *
	 */
	private List<MessageBodyPart> bodyParts;

	public List<MessageBodyPart> getBodyParts() {
		return bodyParts;
	}

	public void setBodyParts(final List<MessageBodyPart> bodyParts) {
		this.bodyParts = bodyParts;
	}
}
