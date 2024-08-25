package epf.file.internal;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.file.client.FileEvent;
import epf.util.concurrent.ext.Emitter;
import epf.util.concurrent.ext.EventEmitter;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class FileWatchService implements HealthCheck {
	
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
	transient Event<FileEvent> events;
	
	/**
	 * 
	 */
	@Inject
	transient FileSystem system;
	
	/**
	 *
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	void postConstruct() {
		emitter = new EventEmitter<>(events);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	void preDestroy() {
		fileWatches.forEach((p, watch) -> {
			try {
				watch.close();
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[FileWatchService.preDestroy]", e);
			}
		});
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
				return fileWatch;
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[FileWatchService.register]", e);
				return null;
			}
		});
	}

	@Override
	public HealthCheckResponse call() {
		if(executor.isShutdown() || executor.isTerminated()) {
			return HealthCheckResponse.down("epf-file-file-watch");
		}
		return HealthCheckResponse.up("epf-file-file-watch");
	}
}
