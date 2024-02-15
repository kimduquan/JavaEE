package epf.messaging;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.messaging.schema.ByteMessage;
import epf.messaging.schema.TextMessage;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.column.ColumnTemplate;

/**
 * 
 */
@ApplicationScoped
public class Messaging {

	/**
	 * 
	 */
	@Inject
    @Channel("epf-messaging-text-out")
	transient Emitter<TextMessage> text;
	
	@Inject
    @Channel("epf-messaging-bytes-out")
	transient Emitter<ByteMessage> bytes;
	
	/**
	 * 
	 */
	@Inject
	transient ColumnTemplate column;
	
	/**
	 * @param message
	 */
	@Incoming("epf-messaging-text-in")
	@RunOnVirtualThread
	public void onMessage(final TextMessage message) {
		column.insert(message);
		text.send(message);
	}
	
	/**
	 * @param message
	 */
	@Incoming("epf-messaging-bytes-in")
	@RunOnVirtualThread
	public void onMessage(final ByteMessage message) {
		column.insert(message);
		bytes.send(message);
	}
}
