package epf.file.internal;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ScheduledFuture;
import epf.client.file.EventKind;
import epf.client.file.FileEvent;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;

public class FileWatch implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private transient final MessageQueue events;
	
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
	 * @param events
	 */
	public FileWatch(final Path path, final WatchService watchService, final MessageQueue events) {
		this.events = events;
		this.path = path;
		this.watchService = watchService;
	}

	@Override
	public void run() {
		final WatchKey watchKey = watchService.poll();
		if(watchKey != null) {
			for (WatchEvent<?> event : watchKey.pollEvents()) {
		        final FileEvent fileEvent = new FileEvent(path, event.context(), event.count(), EventKind.valueOf(event.kind().name()));
		        events.add(new Message(fileEvent));
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
