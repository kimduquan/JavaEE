package epf.file.internal;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import epf.client.file.FileEvent;
import epf.util.logging.LogManager;

/**
 * 
 */
@ApplicationScoped
public class FileCache {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(FileCache.class.getName());

	/**
	 *
	 */
	private final transient Map<Path, MappedByteBuffer> files = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient FileSystem system;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		files.forEach((p, buffer) -> {
			buffer.clear();
		});
	}
	
	/**
	 * @param event
	 */
	public void observes(
			@ObservesAsync 
			final FileEvent event) {
		switch(event.getKind()) {
			case ENTRY_CREATE:
				break;
			case ENTRY_DELETE:
			case ENTRY_MODIFY:
				final Path path = system.getPath(event.getSource()).resolve(event.getContext());
				files.remove(path).clear();
				break;
			case OVERFLOW:
				break;
			default:
				break;
		}
	}
	
	/**
	 * @param path
	 * @throws Exception
	 */
	public Optional<FileOutput> getFile(final Path path) throws Exception {
		Optional<FileOutput> output = Optional.empty();
		final MappedByteBuffer buffer = files.computeIfAbsent(path, p -> {
			try(RandomAccessFile file = new RandomAccessFile(p.toFile(), "r")) 
		    {
				try(FileChannel channel = file.getChannel()){
					final MappedByteBuffer newBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
					return newBuffer.load();
				}
		    }
			catch(Throwable ex) {
				LOGGER.log(Level.SEVERE, "[FileCache.read]", ex);
				return null;
			}
		});
		if(buffer != null) {
			Optional.of( new FileOutput(buffer));
		}
		return output;
	}
}
