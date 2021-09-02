/**
 * 
 */
package epf.util.websocket;

import java.util.function.Consumer;
import epf.util.concurrent.ObjectTopic;

/**
 * @author PC
 *
 */
public class MessageTopic extends ObjectTopic<Message, Consumer<Message>> {

	@Override
	public void accept(final Message message) {
		super.accept(message);
		message.close();
	}
}
