package epf.workflow.util;

import epf.naming.Naming;
import jakarta.ws.rs.core.Link;

/**
 * 
 */
public class LinkBuilder {

	/**
	 * 
	 */
	private transient Link.Builder builder = null;
	
	/**
	 * @param link
	 * @return
	 */
	public LinkBuilder link(final Link link) {
		return this;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public LinkBuilder at(final int index) {
		builder = builder.title(String.valueOf(index));
		return this;
	}
	
	/**
	 * @return
	 */
	public LinkBuilder self() {
		builder = builder.rel(Naming.Client.Link.SELF);
		return this;
	}
	
	/**
	 * @param _synchronized
	 * @return
	 */
	public LinkBuilder Synchronized(final boolean _synchronized) {
		builder = builder.param("synchronized", String.valueOf(_synchronized));
		return this;
	}
	
	/**
	 * @return
	 */
	public Link build() {
		return builder.build();
	}
}
