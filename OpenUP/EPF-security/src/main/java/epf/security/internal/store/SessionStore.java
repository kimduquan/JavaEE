package epf.security.internal.store;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.security.internal.Session;
import epf.security.schema.Token;
import epf.security.util.Credential;
import epf.util.MapUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class SessionStore {
	
    /**
     * 
     */
    private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
        sessions.clear();
    }
    
    /**
     * @return
     */
    public Optional<Session> getSession(final SecurityContext context) {
    	Objects.requireNonNull(context, "SecurityContext");
    	final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	Objects.requireNonNull(jwt.getTokenID(), "JsonWebToken.tokenId");
    	return MapUtil.get(sessions, jwt.getTokenID());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Session> getSession(final Token token){
    	Objects.requireNonNull(token, "Token");
    	Objects.requireNonNull(token.getTokenID(), "Token.tokenId");
    	return MapUtil.get(sessions, token.getTokenID());
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Session> removeSession(final SecurityContext context) {
    	Objects.requireNonNull(context, "SecurityContext");
    	final JsonWebToken jwt = (JsonWebToken)context.getUserPrincipal();
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	Objects.requireNonNull(jwt.getTokenID(), "JsonWebToken.tokenId");
    	return MapUtil.remove(sessions, jwt.getTokenID());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Session> removeSession(final Token token) {
    	Objects.requireNonNull(token, "Token");
    	Objects.requireNonNull(token.getTokenID(), "Token.tokenId");
    	return MapUtil.remove(sessions, token.getTokenID());
    }
    
    /**
     * @param token
     * @param credential
     * @return
     */
    public Session putSession(final Token token, final Credential credential){
    	Objects.requireNonNull(token, "Token");
    	Objects.requireNonNull(token.getTokenID(), "Token.tokenId");
    	final Session session = new Session(token, credential);
        MapUtil.put(sessions, token.getTokenID(), session);
        return session;
    }
}
