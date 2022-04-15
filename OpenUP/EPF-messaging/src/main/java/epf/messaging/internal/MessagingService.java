package epf.messaging.internal;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
@Path("conversations")
public interface MessagingService {

	/**
	 * @param tenant
	 * @param one
	 * @param other
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Conversation getConversation(
			@MatrixParam("tenant")
			final String tenant,
			@QueryParam("one")
			final String one, 
			@QueryParam("other")
			final String other);
	
	/**
	 * @param tenant
	 * @param one
	 * @param other
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	Conversation newConversation(
			@MatrixParam("tenant")
			final String tenant,
			@FormParam("one")
			final String one, 
			@FormParam("other")
			final String other);
	
	/**
	 * @param tenant
	 * @param cid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@Path("{cid}/messages")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<Message> getMessages(
			@MatrixParam("tenant")
			final String tenant,
			@PathParam("cid")
			final String cid,
			@QueryParam("first")
			final Integer firstResult,
			@QueryParam("max")
			final Integer maxResults);
	
	/**
	 * @param tenant
	 * @param cid
	 * @param message
	 * @return
	 */
	@Path("{cid}/messages")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	String newMessage(
			@MatrixParam("tenant")
			final String tenant,
			@PathParam("cid")
			final String cid,
			final Message message);
}
