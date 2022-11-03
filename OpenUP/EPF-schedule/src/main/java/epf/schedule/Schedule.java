package epf.schedule;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.Client;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.SCHEDULE)
public class Schedule implements epf.client.schedule.Schedule {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(Schedule.class.getName());
	
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
	private transient MessageQueue shellMessages;
	
	/**
	 * 
	 */
	private transient Client shell;

	/**
	 * 
	 */
	@Resource(lookup = Naming.Schedule.EXECUTOR_SERVICE)
	private transient ManagedScheduledExecutorService scheduledExecutor;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			shell = Client.connectToServer(messagingUrl.resolve("schedule/shell"));
			shellMessages = new MessageQueue(shell.getSession());
			executor.submit(shellMessages);
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		shellMessages.close();
		invokers.values().parallelStream().forEach(Scheduled::close);
		invokers.clear();
		try {
			shell.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
	}

	@Override
	public long schedule(final String path, final long delay, final TimeUnit unit) {
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), shellMessages);
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
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), shellMessages);
		final ScheduledFuture<?> future = scheduledExecutor.scheduleAtFixedRate(invoke, initialDelay, period, unit);
		invoke.setScheduled(future);
		invokers.put(invoke.getId(), invoke);
		return invoke.getId();
	}

	@Override
	public long scheduleWithFixedDelay(final String path, final long initialDelay, final long delay, final TimeUnit unit) {
		final Scheduled invoke = new Scheduled(id.incrementAndGet(), shellMessages);
		final ScheduledFuture<?> future = scheduledExecutor.scheduleWithFixedDelay(invoke, initialDelay, delay, unit);
		invoke.setScheduled(future);
		invokers.put(invoke.getId(), invoke);
		return invoke.getId();
	}
}
