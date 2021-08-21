/**
 * 
 */
package epf.persistence.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
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

	public Object getObject() {
		return object;
	}
	
	public void waitObject() {
		do {
			try {
				Thread.sleep(40);
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "waitToSend", e);
			}
		}
		while(!isClose);
	}
}
