/**
 * 
 */
package epf.gateway.messaging;

/**
 * @author PC
 *
 */
public class Message {

	/**
	 * 
	 */
	private final String text;
	
	/**
	 * @param text
	 */
	public Message(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
