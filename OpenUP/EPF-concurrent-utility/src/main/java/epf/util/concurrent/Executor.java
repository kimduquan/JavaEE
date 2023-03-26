package epf.util.concurrent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Executor {
    
    /**
     * @return
     */
    @Produces
    @ApplicationScoped
    public ManagedExecutor getExecutor(){
        return ManagedExecutor
                .builder()
                .propagated(ThreadContext.APPLICATION)
                .build();
    }
    
    /**
     * @param executor
     */
    public void closeExecutor(@Disposes final ManagedExecutor executor) {
    	executor.shutdownNow();
    }
}
