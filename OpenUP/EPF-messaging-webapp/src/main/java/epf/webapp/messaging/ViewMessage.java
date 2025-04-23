package epf.webapp.messaging;

import java.io.Serializable;
import jakarta.faces.application.FacesMessage;

/**
 * @author PC
 *
 */
public class ViewMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String ERROR = "ERROR";
	
	/**
	 * 
	 */
	public static final String INFO = "INFO";
	
	/**
	 * 
	 */
	public static final String WARN = "WARN";
	
	/**
	 * 
	 */
	public static final String FATAL = "FATAL";
	
	/**
	 * 
	 */
	private final FacesMessage message;
	
	/**
	 * @param message
	 */
	public ViewMessage(final FacesMessage message) {
		this.message = message;
	}

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

	public String getSummary() {
		return message.getSummary();
	}

	public String getDetail() {
		return message.getDetail();
	}

}
