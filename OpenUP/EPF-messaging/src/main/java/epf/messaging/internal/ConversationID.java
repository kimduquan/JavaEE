package epf.messaging.internal;

import javax.persistence.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ConversationID {

	/**
	 * 
	 */
	private String one;
	/**
	 * 
	 */
	private String other;
	
	public String getOne() {
		return one;
	}
	public void setOne(final String one) {
		this.one = one;
	}
	public String getOther() {
		return other;
	}
	public void setOther(final String other) {
		this.other = other;
	}
}
