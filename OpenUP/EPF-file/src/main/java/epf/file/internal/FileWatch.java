package epf.file.internal;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.file.client.EventKind;
import epf.file.client.FileEvent;
import epf.util.concurrent.ext.Emitter;
import epf.util.logging.LogManager;

/**
 * 
 */
public class FileWatch implements Runnable, Closeable {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(FileWatch.class.getName());
	
	/**
	 * 
	 */
	private transient final Emitter<FileEvent> emitter;
	
	/**
	 * 
	 */
	private transient final Path path;
	
	/**
	 * 
	 */
	private transient final WatchService watchService;
	
	/**
	 * 
	 */
	private transient ScheduledFuture<?> result;
	
	/**
	 * @param path
	 * @param watchService
	 * @param emitter
	 */
	public FileWatch(final Path path, final WatchService watchService, final Emitter<FileEvent> emitter) {
		this.emitter = emitter;
		this.path = path;
		this.watchService = watchService;
	}

	@Override
	public void run() {
		final WatchKey watchKey = watchService.poll();
		if(watchKey != null) {
			for (WatchEvent<?> event : watchKey.pollEvents()) {
		        final FileEvent fileEvent = new FileEvent(path, event.context(), event.count(), EventKind.valueOf(event.kind().name()));
		        try {
					emitter.sendAsync(fileEvent);
				} 
		        catch (Exception e) {
					LOGGER.log(Level.SEVERE, "[FileWatch.run]", e);
				}
		    }
			watchKey.reset();
		}
	}

	public ScheduledFuture<?> getResult() {
		return result;
	}

	public void setResult(final ScheduledFuture<?> result) {
		this.result = result;
	}

	@Override
	public void close() throws IOException {
		result.cancel(true);
		watchService.close();
	}
}
