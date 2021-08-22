/**
 * 
 */
package epf.util.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class Message implements Serializable, Closeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Message.class.getName());
	
	/**
	 * 
	 */
	private transient final Object object;
	
	/**
	 * 
	 */
	private transient boolean isClose;
	
	/**
	 * @param object
	 */
	public Message(final Object object) {
		this.object = object;
		isClose = false;
	}

	@Override
	public void close() throws IOException {
		isClose = true;
	}

	/**
	 * @param session
	 * @throws EncodeException 
	 * @throws IOException 
	 */
	protected void send(final Session session) throws IOException, EncodeException {
		if(object instanceof String) {
			session.getBasicRemote().sendText((String) object);
		}
		else {
			session.getBasicRemote().sendObject(object);
		}
	}
	
	/**
	 * 
	 */
	public void waitToClose() {
		do {
			try {
				Thread.sleep(40);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "waitToClose", e);
			}
		}
		while(!isClose);
	}
}
