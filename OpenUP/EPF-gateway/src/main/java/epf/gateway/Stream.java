package epf.gateway;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import epf.gateway.util.Streaming;
import jakarta.enterprise.context.ApplicationScoped;
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
	private final List<Session> sessions = new CopyOnWriteArrayList<>();
	
	private void setOpenSessions(final Session session) {
		sessions.clear();
        sessions.addAll(session.getOpenSessions());
	}
	
	@OnOpen
	public void onOpen(final Session session) {
		setOpenSessions(session);
	}
	
	@OnClose
	public void onClose(final Session session) {
		setOpenSessions(session);
	}
	
	@OnError
	public void onError(final Session session) {
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
