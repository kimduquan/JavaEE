package epf.messaging.internal;

/**
 * 
 */
public class TextMessage extends Message {

	/**
	 * 
	 */
	private String text;

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
}
