/**
 * 
 */
package epf.lang;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/lang")
@ApplicationScoped
public class Lang {

	/**
	 * @param session
	 * @param config
	 */
	@OnOpen
    public void onOpen(final Session session, final EndpointConfig config) {
		
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
		
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(final Session session, final Throwable throwable) {
		
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final Object message, final Session session) {
		
	}
}
