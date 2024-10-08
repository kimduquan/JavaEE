package epf.util.websocket.ext;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;

/**
 * @author PC
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient final Object object;
	
	/**
	 * @param object
	 */
	public Message(final Object object) {
		this.object = object;
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s", getClass().getName(), String.valueOf(object));
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
		else if(object instanceof ByteBuffer) {
			session.getBasicRemote().sendBinary((ByteBuffer)object);
		}
		else {
			session.getBasicRemote().sendObject(object);
		}
	}
}
