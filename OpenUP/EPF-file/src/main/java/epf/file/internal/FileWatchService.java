package epf.file.internal;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.client.file.FileEvent;
import epf.util.event.Emitter;
import epf.util.event.EventEmitter;
import epf.util.event.EventQueue;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class FileWatchService {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(FileWatchService.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<Path, FileWatch> fileWatches = new ConcurrentHashMap<>();
	
	/**
	 *
	 */
	private transient Emitter<FileEvent> emitter;
	
	/**
	 * 
	 */
	@Inject
	private transient EventQueue<FileEvent> events;
	
	/**
	 * 
	 */
	@Inject
	private transient FileSystem system;
	
	/**
	 * 
	 */
	@Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
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
		executor.submit(events);
		emitter = new EventEmitter<>(events);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		fileWatches.forEach((p, watch) -> {
			try {
				watch.close();
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[FileWatchService.preDestroy]", e);
			}
		});
		events.close();
	}
	
	/**
	 * @param path
	 */
	public void register(final Path path) {
		Objects.requireNonNull(path, "Path");
		fileWatches.computeIfAbsent(path, name -> {
			try {
				final WatchService watchService = system.newWatchService();
				path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
				final FileWatch fileWatch = new FileWatch(path, watchService, emitter);
				fileWatch.setResult(scheduledExecutor.scheduleWithFixedDelay(fileWatch, 0, 40, TimeUnit.MILLISECONDS));
				return fileWatch;
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[FileWatchService.register]", e);
				return null;
			}
		});
	}
}
