package epf.gateway;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.gateway.util.Streaming;
import epf.util.logging.LogManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * 
 */
@ApplicationScoped
@ServerEndpoint("/stream")
public class Stream {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(Stream.class.getName());

	/**
	 * 
	 */
	private final List<Session> sessions = new CopyOnWriteArrayList<>();
	
	private void setOpenSessions(final Session session) {
		sessions.clear();
        sessions.addAll(session.getOpenSessions());
	}
	
	@OnOpen
	public void onOpen(final Session session) {
		LOGGER.info("onOpen:" + session.getId());
		setOpenSessions(session);
	}
	
	@OnClose
	public void onClose(final Session session, final CloseReason closeReason) {
		LOGGER.info("onClose:" + session.getId() + "," + closeReason.getReasonPhrase() + "," + closeReason.getCloseCode());
		setOpenSessions(session);
	}
	
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		LOGGER.log(Level.SEVERE, "onError", throwable);
		setOpenSessions(session);
	}
	
	/**
	 * @param id
	 * @param input
	 */
	public Streaming stream(final String id, final CompletionStage<InputStream> input) {
		final Optional<Session> session = sessions.stream().filter(ss -> ss.getId().equals(id)).findFirst();
		if(session.isPresent()) {
			return Streaming.stream(session.get(), input);
		}
		return null;
	}
}
