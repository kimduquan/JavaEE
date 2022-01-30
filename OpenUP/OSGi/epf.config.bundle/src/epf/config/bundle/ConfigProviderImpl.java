package epf.config.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.osgi.service.component.annotations.*;
import epf.config.Config;
import epf.config.ConfigProvider;
import epf.file.FileSystem;

@Component
public class ConfigProviderImpl implements ConfigProvider {
	
	/**
	 * 
	 */
	private transient Config config;
	
	/**
	 * 
	 */
	private transient FileSystem fileSystem;

	@Override
	public Config getConfig() {
		return config;
	}
	
	@Reference(name = "epf.file", service = FileSystem.class)
	public void bindFileSystem(final FileSystem files) {
		this.fileSystem = files;
	}
	
	public void unbindFileSystem(final FileSystem files) {
		this.fileSystem = null;
	}
	
	@Activate
	public void activate() throws IOException {
		final Path configFile = fileSystem.getPath("", "epf.config");
		try(InputStream input = Files.newInputStream(configFile)){
			final Properties props = new Properties();
			props.load(input);
			config = new ConfigImpl(props);
		}
	}
	
	@Deactivate
	public void deactivate() {
		
	}
}
