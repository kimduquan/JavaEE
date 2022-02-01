package epf.persistence.internal;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.persistence.security.auth.EPFPrincipal;
import epf.security.schema.Token;
import epf.util.MapUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    /**
     * 
     */
    private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
        sessions.forEach((id, session) -> {
    		session.close();
        });
        sessions.clear();
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Session> getSession(final JsonWebToken jwt){
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
    public Optional<Session> removeSession(final JsonWebToken jwt) {
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
    public Session putSession(final EPFPrincipal principal, final Token token){
    	Objects.requireNonNull(principal, "EPFPrincipal");
    	Objects.requireNonNull(token, "Token");
    	Objects.requireNonNull(token.getTokenID(), "Token.tokenId");
        return sessions.computeIfAbsent(token.getTokenID(), time -> new Session(principal, token) );
    }
    
    /**
     * @param userName
     * @return
     */
    public Optional<Session> findSession(final String userName){
    	return sessions.values().parallelStream().filter(session -> session.getToken().getName().equals(userName)).findFirst();
    }
}
