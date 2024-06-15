package epf.gateway.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;

/**
 * 
 */
@ClientEndpoint
public class Streaming implements BiFunction<InputStream, Throwable, InputStream> {
	
	/**
	 * 
	 */
	private final AtomicReference<Throwable> throwable = new AtomicReference<>();
	
	/**
	 * 
	 */
	private Session session;

	@Override
	public InputStream apply(final InputStream stream, Throwable error) {
		if(error != null) {
			throwable.set(error);
		}
		else {
			try {
				send(session, stream);
			}
	    	catch(Exception ex) {
	    		throwable.set(ex);
	    	}
		}
		return stream;
	}

	public Throwable getThrowable() {
		return throwable.get();
	}
	
	private void send(final Session session, final InputStream stream) throws Exception {
		try(stream){
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
				String line;
    		    while ((line = reader.readLine()) != null) {
    		        if(session.isOpen()) {
    		        	session.getBasicRemote().sendText(line);
    		        }
    		        else {
    		        	break;
    		        }
    		    }
    		}
		}
	}
	
	/**
	 * @param id
	 * @param stream
	 * @throws Exception
	 */
	public void send(final String id, final InputStream stream) throws Exception {
		final Optional<Session> session = this.session.getOpenSessions().stream().filter(ss -> ss.getId().equals(id)).findFirst();
		if(session.isPresent()) {
			send(session.get(), stream);
		}
	}
	
	/**
	 * @param stream
	 * @throws Exception
	 */
	public void send(final InputStream stream) throws Exception {
		send(session, stream);
	}
	
	/**
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static Streaming connectToServer(final URI uri) throws Exception {
		final Streaming stream = new Streaming();
		stream.session = ContainerProvider.getWebSocketContainer().connectToServer(stream, uri);
		return stream;
	}
	
	/**
	 * @param session
	 * @param input
	 * @return
	 */
	public static Streaming stream(final Session session, final CompletionStage<InputStream> input) {
		final Streaming stream = new Streaming();
		stream.session = session;
		input.handleAsync(stream);
		return stream;
	}
}
