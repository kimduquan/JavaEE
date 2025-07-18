package epf.webapp.messaging;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import epf.webapp.naming.Naming.Messaging;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Messaging.VIEW)
public class View implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<ViewMessage> messages;
	
	/**
	 * 
	 */
	@Inject
	private transient FacesContext context;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		messages = context.getMessageList().stream().map(ViewMessage::new).collect(Collectors.toList());
	}

	public List<ViewMessage> getMessages() {
		return messages;
	}
}
