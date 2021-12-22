package epf.persistence.internal;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import javax.persistence.EntityManager;
import epf.persistence.security.auth.EPFPrincipal;
import epf.security.schema.Token;
import epf.util.QueueUtil;

/**
 *
 * @author FOXCONN
 */
public class Session {
   
    /**
     * 
     */
    private transient final EPFPrincipal principal;
    
    /**
     * 
     */
    private transient final Queue<EntityManager> managers;
    
    /**
     * 
     */
    private final Token token;

    /**
     * @param principal
     */
    public Session(final EPFPrincipal principal, final Token token) {
    	this.principal = principal;
    	managers = new ConcurrentLinkedQueue<>();
		this.token = token;
    }

    /**
     * 
     */
    public void close() {
    	managers.forEach(manager -> {
    		manager.close();
        });
    	managers.clear();
    }
    
    /**
     * @param <R>
     * @param function
     * @return
     */
    public <R> Optional<R> peekManager(final Function<EntityManager, R> function) {
    	Objects.requireNonNull(function, "Function");
    	QueueUtil.ifEmpty(managers, principal::newManager);
    	return QueueUtil.peek(managers, function);
    }

	public Token getToken() {
		return token;
	}

	public EPFPrincipal getPrincipal() {
		return principal;
	}
}
