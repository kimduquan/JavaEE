package epf.file.cache;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import epf.file.client.FileEvent;
import epf.util.io.ByteBufferUtil;
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
		files.clear();
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
				final MappedByteBuffer buffer = files.remove(path);
				if(buffer != null) {
					buffer.clear();
					try {
						ByteBufferUtil.clean(buffer);
					} 
					catch (Exception e) {
						LOGGER.log(Level.SEVERE, "[FileCache.observes]clean path=" + path, e);
					}
					LOGGER.log(Level.INFO, "[FileCache.observes]remove path=" + path);
				}
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
		Objects.requireNonNull(path, "Path");
		Optional<FileOutput> fileOutput = Optional.empty();
		final MappedByteBuffer fileBuffer = files.computeIfAbsent(path, file -> {
			LOGGER.log(Level.INFO, "[FileCache.getFile]load path=" + path);
			MappedByteBuffer newFileBuffer = null;
			try(RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r")) 
		    {
				try(FileChannel fileChannel = raf.getChannel()){
					newFileBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
					return newFileBuffer.load();
				}
		    }
			catch(Throwable ex) {
				LOGGER.log(Level.SEVERE, "[FileCache.getFile]path=" + path, ex);
			}
			return null;
		});
		if(fileBuffer != null) {
			Optional.of( new FileOutput(fileBuffer));
		}
		return fileOutput;
	}
}
