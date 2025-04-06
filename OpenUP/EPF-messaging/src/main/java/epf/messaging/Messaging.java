package epf.messaging;

import org.eclipse.jnosql.mapping.column.ColumnTemplate;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.messaging.schema.ByteMessage;
import epf.messaging.schema.TextMessage;
import epf.naming.Naming.Messaging.Internal;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped 
@Readiness
public class Messaging implements HealthCheck {

	@Inject
    @Channel(Internal.EPF_MESSAGING_TEXT_OUT)
	transient Emitter<TextMessage> text;
	
	@Inject
    @Channel(Internal.EPF_MESSAGING_BYTES_OUT)
	transient Emitter<ByteMessage> bytes;
	
	@Inject
	transient ColumnTemplate column;
	
	@Incoming(Internal.EPF_MESSAGING_TEXT_IN)
	@RunOnVirtualThread
	public void onMessage(final TextMessage message) {
		text.send(column.insert(message));
	}
	
	@Incoming(Internal.EPF_MESSAGING_BYTES_IN)
	@RunOnVirtualThread
	public void onMessage(final ByteMessage message) {
		bytes.send(column.insert(message));
	}

	@Override
	public HealthCheckResponse call() {
		if(text.isCancelled()) {
			return HealthCheckResponse.down(Internal.EPF_MESSAGING_TEXT_OUT);
		}
		if(bytes.isCancelled()) {
			return HealthCheckResponse.down(Internal.EPF_MESSAGING_BYTES_OUT);
		}
		return HealthCheckResponse.up("epf-messaging");
	}
}
