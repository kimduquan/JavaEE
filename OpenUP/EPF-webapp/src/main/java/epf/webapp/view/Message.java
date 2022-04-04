package epf.webapp.view;

import java.io.Serializable;
import javax.faces.application.FacesMessage;

/**
 * @author PC
 *
 */
public class Message implements MessageView, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final FacesMessage message;
	
	/**
	 * @param message
	 */
	public Message(final FacesMessage message) {
		this.message = message;
		
	}

	@Override
	public String getSeverity() {
		String severity = "";
		if(FacesMessage.SEVERITY_ERROR.equals(message.getSeverity())) {
			severity = ERROR;
		}
		else if(FacesMessage.SEVERITY_FATAL.equals(message.getSeverity())) {
			severity = FATAL;
		}
		else if(FacesMessage.SEVERITY_INFO.equals(message.getSeverity())) {
			severity = INFO;
		}
		else if(FacesMessage.SEVERITY_WARN.equals(message.getSeverity())) {
			severity = WARN;
		}
		return severity;
	}

	@Override
	public String getSummary() {
		return message.getSummary();
	}

	@Override
	public String getDetail() {
		return message.getDetail();
	}

}
