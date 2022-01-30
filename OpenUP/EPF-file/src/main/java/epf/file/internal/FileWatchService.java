package epf.file.internal;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.file.IWatchService;
import epf.messaging.client.Client;
import epf.messaging.client.Messaging;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class FileWatchService implements IWatchService {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(FileWatchService.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, FileWatch> fileWatches = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient MessageQueue events;
	
	/**
	 * 
	 */
	@Inject
	private transient FileSystem system;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
	private transient ManagedScheduledExecutorService scheduledExecutor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			client = Messaging.connectToServer(ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL).resolve(Naming.FILE));
			client.onMessage(msg -> {});
			events = new MessageQueue(client.getSession());
			executor.submit(events);
		}
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		events.close();
		try {
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
		fileWatches.values().parallelStream().forEach(watch -> {
			try {
				watch.close();
			} 
			catch (IOException e) {
				LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
			}
		});
	}
	
	/**
	 * @param security
	 */
	@Override
	public void register(final Path path) {
		fileWatches.computeIfAbsent(path.toString(), name -> {
			try {
				final WatchService watchService = system.newWatchService();
				path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
				final FileWatch fileWatch = new FileWatch(path, watchService, events);
				fileWatch.setResult(scheduledExecutor.scheduleWithFixedDelay(fileWatch, 0, 40, TimeUnit.MILLISECONDS));
				return fileWatch;
			} 
			catch (IOException e) {
				LOGGER.throwing(LOGGER.getName(), "register", e);
				return null;
			}
		});
	}
}
