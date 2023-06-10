package epf.schedule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.schedule.internal.Messaging;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.SCHEDULE)
public class Schedule implements epf.schedule.client.Schedule {
	
	/**
	 * 
	 */
	private transient final AtomicLong id = new AtomicLong(0);
	
	/**
	 * 
	 */
	private transient final Map<Long, Scheduled> invokers = new ConcurrentHashMap<>();

	/**
	 * 
	 */
	@Resource(lookup = Naming.Schedule.EXECUTOR_SERVICE)
	private transient ManagedScheduledExecutorService scheduledExecutor;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	private transient Messaging messaging;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		invokers.values().parallelStream().forEach(Scheduled::close);
		invokers.clear();
	}

	@Override
	public long schedule(final String path, final long delay, final TimeUnit unit) {
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), messaging.getMessages());
		final ScheduledFuture<?> future = scheduledExecutor.schedule(invoke, delay, unit);
		invoke.setScheduled(future);
		invokers.put(invoke.getId(), invoke);
		return invoke.getId();
	}

	@Override
	public void cancel(final String path, final long id) {
		if(invokers.containsKey(id)) {
			invokers.remove(id).close();
		}
	}

	@Override
	public long scheduleAtFixedRate(final String path, final long initialDelay, final long period, final TimeUnit unit) {
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), messaging.getMessages());
		final ScheduledFuture<?> future = scheduledExecutor.scheduleAtFixedRate(invoke, initialDelay, period, unit);
		invoke.setScheduled(future);
		invokers.put(invoke.getId(), invoke);
		return invoke.getId();
	}

	@Override
	public long scheduleWithFixedDelay(final String path, final long initialDelay, final long delay, final TimeUnit unit) {
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), messaging.getMessages());
		final ScheduledFuture<?> future = scheduledExecutor.scheduleWithFixedDelay(invoke, initialDelay, delay, unit);
		invoke.setScheduled(future);
		invokers.put(invoke.getId(), invoke);
		return invoke.getId();
	}
}
