/**
 * 
 */
package epf.util.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.websocket.EncodeException;
import javax.websocket.Session;

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
	private transient final Object object;
	
	/**
	 * 
	 */
	private transient final AtomicBoolean isClose;
	
	/**
	 * @param object
	 */
	public Message(final Object object) {
		this.object = object;
		isClose = new AtomicBoolean(false);
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s", getClass().getName(), String.valueOf(object));
	}

	@Override
	public void close() {
		isClose.set(true);
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
}
