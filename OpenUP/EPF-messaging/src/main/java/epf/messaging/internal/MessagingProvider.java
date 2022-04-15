package epf.messaging.internal;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.Schema;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class MessagingProvider implements MessagingService {
	
	/**
	 * 
	 */
	@Inject
	private transient Request request;
	
	/**
	 * @return
	 */
	private Message newMessage() {
		return new Message();
	}

	@Override
	public Conversation getConversation(final String tenant, final String one, final String other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMessages(final String tenant, final String cid, final Integer firstResult, final Integer maxResults) {
		final Schema schema = request.getSession().getSchema(Naming.EPF + "_" + Naming.MESSAGING + "_" + tenant);
		final DbDoc conversation = schema.getCollection("conversations").getOne(cid);
		if(conversation != null) {
			final List<Message> messages = new ArrayList<>();
			schema.getCollection("messages").find("cid = :id").bind("id", cid).execute().forEach(result -> {
				messages.add(newMessage());
			});
			return messages;
		}
		throw new NotFoundException();
	}

	@Override
	public Conversation newConversation(final String tenant, final String one, final String other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String newMessage(final String tenant, final String cid, final Message message) {
		// TODO Auto-generated method stub
		return null;
	}
}
