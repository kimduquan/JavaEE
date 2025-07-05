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
import jakarta.websocket.RemoteEndpoint.Basic;
import jakarta.websocket.Session;

@ClientEndpoint
public class Streaming implements BiFunction<InputStream, Throwable, InputStream> {
	
	private final AtomicReference<Throwable> throwable = new AtomicReference<>();
	
	private Session session;

	@Override
	public InputStream apply(final InputStream stream, Throwable error) {
		return stream(session, stream, error, throwable);
	}

	public Throwable getThrowable() {
		return throwable.get();
	}
	
	private static InputStream stream(final Session session, final InputStream stream, Throwable error, final AtomicReference<Throwable> throwable) {
		if(error != null) {
			throwable.set(error);
		}
		else {
			try {
				send(session, stream);
			}
	    	catch(Throwable ex) {
	    		throwable.set(ex);
	    	}
		}
		return stream;
	}
	
	private static InputStream send(final Session session, final InputStream stream) throws Exception {
		try(stream){
			final Basic remote = session.getBasicRemote();
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
				String line;
    		    while ((line = reader.readLine()) != null) {
    		        if(session.isOpen()) {
    		        	remote.sendText(line);
    		        }
    		        else {
    		        	break;
    		        }
    		    }
    			return stream;
    		}
		}
	}
	
	public void send(final String id, final CompletionStage<InputStream> input) throws Exception {
		final Optional<Session> session = this.session.getOpenSessions().stream().filter(ss -> ss.getId().equals(id)).findFirst();
		if(session.isPresent()) {
			input.handleAsync((in, err) -> stream(session.get(), in, err, throwable));
		}
	}
	
	public void send(final InputStream stream) throws Exception {
		send(session, stream);
	}
	
	public static Streaming connectToServer(final URI uri) throws Exception {
		final Streaming stream = new Streaming();
		stream.session = ContainerProvider.getWebSocketContainer().connectToServer(stream, uri);
		return stream;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
