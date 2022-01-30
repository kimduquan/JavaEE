package epf.messaging;

import java.io.Closeable;
import java.util.function.Consumer;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author PC
 *
 */
@ConsumerType
public interface MessageConsumer extends Consumer<Message>, Closeable {

}
