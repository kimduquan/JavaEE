package epf.messaging.internal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author PC
 *
 */
@Entity
public class Conversation {

	/**
	 * 
	 */
	@EmbeddedId
	private ConversationID ID;
	/**
	 * 
	 */
	private String CID;
	
	public ConversationID getID() {
		return ID;
	}
	public void setID(final ConversationID iD) {
		ID = iD;
	}
	public String getCID() {
		return CID;
	}
	public void setCID(final String cID) {
		CID = cID;
	}
}
