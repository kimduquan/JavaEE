package epf.webapp.view;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.MESSAGES)
public class Messages implements MessagesView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<MessageView> messages;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		messages = FacesContext.getCurrentInstance().getMessageList().stream().map(Message::new).collect(Collectors.toList());
	}

	@Override
	public List<MessageView> getMessages() {
		return messages;
	}
}
