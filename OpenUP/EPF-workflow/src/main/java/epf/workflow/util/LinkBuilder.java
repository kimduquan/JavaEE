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
	 * @param synchronized_
	 * @return
	 */
	public LinkBuilder synchronized_(final String synchronized_) {
		builder = builder.param("synchronized", synchronized_);
		return this;
	}
	
	/**
	 * @param synchronized_
	 * @return
	 */
	public LinkBuilder continue_(final String synchronized_) {
		builder = builder.param("continue", synchronized_);
		return this;
	}
	
	/**
	 * @return
	 */
	public Link build() {
		return builder.build();
	}
}
