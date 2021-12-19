package epf.persistence.internal;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.persistence.security.internal.EPFPrincipal;
import epf.security.schema.Token;
import epf.util.MapUtil;

/**
 *
 * @author FOXCONN
 */
public class Credential {
    
    /**
     * 
     */
    private transient final Map<String, Session> sessions;
    /**
     * 
     */
    private transient final EPFPrincipal principal;

    /**
     * @param principal
     */
    public Credential(final EPFPrincipal principal) {
        sessions = new ConcurrentHashMap<>();
		this.principal = principal;
    }

    /**
     * @throws IOException 
     * 
     */
    public void close() throws IOException {
    	sessions.forEach((time, session) -> {
    		session.close();
        });
        sessions.clear();
        principal.close();
    }
    
    /**
     * @param token
     * @return
     */
    public Session putSession(final Token token){
    	Objects.requireNonNull(token, "Token");
        return sessions.computeIfAbsent(token.getTokenID(), time -> {
            return new Session(principal, token);
        });
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Session> getSessioṇ̣(final JsonWebToken jwt) {
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	return MapUtil.get(sessions, jwt.getTokenID());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Session> getSessioṇ̣(final Token token) {
    	Objects.requireNonNull(token, "Token");
    	return MapUtil.get(sessions, token.getTokenID());
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Session> removeSessioṇ̣(final JsonWebToken jwt) {
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	return MapUtil.remove(sessions, jwt.getTokenID());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Session> removeSessioṇ̣(final Token token) {
    	Objects.requireNonNull(token, "Token");
    	return MapUtil.remove(sessions, token.getTokenID());
    }

	public EPFPrincipal getPrincipal() {
		return principal;
	}
}
