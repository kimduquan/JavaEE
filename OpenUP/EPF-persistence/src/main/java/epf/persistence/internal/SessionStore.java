package epf.persistence.internal;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.CallerPrincipal;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.security.auth.EPFPrincipal;
import epf.security.schema.Token;
import epf.util.MapUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Traced
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
     * @param session
     * @return
     */
    public Session putSession(final CallerPrincipal principal, final Token token){
    	Objects.requireNonNull(principal, "CallerPrincipal");
    	Objects.requireNonNull(token, "Token");
    	Objects.requireNonNull(token.getTokenID(), "Token.tokenId");
    	final Session session = new Session((EPFPrincipal)principal, token);
        MapUtil.put(sessions, token.getTokenID(), session);
        return session;
    }
    
    /**
     * @param userName
     * @return
     */
    public Optional<Session> findSession(final String userName){
    	return MapUtil.findAny(sessions, session -> session.getToken().getName().equals(userName));
    }
}
