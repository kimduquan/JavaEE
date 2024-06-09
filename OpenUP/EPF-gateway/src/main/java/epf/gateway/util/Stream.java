package epf.gateway.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.Session;

/**
 * 
 */
public class Stream implements BiFunction<InputStream, Throwable, InputStream> {
	
	/**
	 * 
	 */
	private final AtomicReference<Throwable> throwable = new AtomicReference<>();
	
	/**
	 * 
	 */
	private final Session session;
	
	/**
	 * @param session
	 */
	public Stream(Session session) {
		Objects.requireNonNull(session, "Session");
		this.session = session;
	}

	@Override
	public InputStream apply(final InputStream stream, Throwable error) {
		if(error != null) {
			throwable.set(error);
		}
		else {
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
        		try(Jsonb jsonb = JsonbBuilder.create()){
            		String line;
        		    while ((line = reader.readLine()) != null) {
        		        if(session.isOpen()) {
        		        	session.getBasicRemote().sendText(line);
        		        }
        		    }
        		}
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

	public Session getSession() {
		return session;
	}
}
