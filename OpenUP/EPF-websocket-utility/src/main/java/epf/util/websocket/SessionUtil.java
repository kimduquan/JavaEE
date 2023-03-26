package epf.util.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import javax.websocket.Session;

/**
 * @author PC
 *
 */
public final class SessionUtil {
	
	/**
	 * 
	 */
	private SessionUtil() {
		
	}

	/**
	 * @param session
	 * @return
	 */
	public static String toString(final Session session) {
		final Map<String, Object> pathParams = new ConcurrentHashMap<>(session.getPathParameters());
		final Map<String, Object> userProps = new ConcurrentHashMap<>(session.getUserProperties());
		return Json.createObjectBuilder()
		.add("id", String.valueOf(session.getId()))
		.add("maxBinaryMessageBufferSize", session.getMaxBinaryMessageBufferSize())
		.add("maxIdleTimeout", session.getMaxIdleTimeout())
		.add("maxTextMessageBufferSize", session.getMaxTextMessageBufferSize())
		.add("negotiatedSubprotocol", String.valueOf(session.getNegotiatedSubprotocol()))
		.add("pathParameters", Json.createObjectBuilder(pathParams).build())
		.add("protocolVersion", String.valueOf(session.getProtocolVersion()))
		.add("queryString", String.valueOf(session.getQueryString()))
		.add("open", session.isOpen())
		.add("secure", session.isSecure())
		.add("requestURI", String.valueOf(session.getRequestURI()))
		.add("userProperties", Json.createObjectBuilder(userProps).build())
		.build().toString();
	}
}
