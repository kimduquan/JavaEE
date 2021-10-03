package epf.logging.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.osgi.service.component.annotations.*;
import epf.config.ConfigProvider;
import epf.config.ConfigValue;
import epf.file.FileSystem;
import epf.logging.LogManager;

@Component
public class LogManagerImpl implements LogManager {
	
	/**
	 * 
	 */
	private transient ConfigProvider configProvider;
	
	/**
	 * 
	 */
	private transient FileSystem fileSystem;
	
	/**
	 * 
	 */
	private transient java.util.logging.LogManager logManager;

	@Override
	public Logger getLogger(final String name) {
		return logManager.getLogger(name);
	}
	
	@Reference(name = "epf.config", service = ConfigProvider.class)
	public void bindConfigProvider(final ConfigProvider config) {
		this.configProvider = config;
	}
	
	public void unbindConfigProvider(final ConfigProvider config) {
		this.configProvider = null;
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
		final ConfigValue config = configProvider.getConfig().getConfigValue("epf.logging.config.file");
		final Path configFile = fileSystem.getPath(config.getValue());
		this.logManager = java.util.logging.LogManager.getLogManager();
		try(InputStream input = Files.newInputStream(configFile)){
			this.logManager.readConfiguration(input);
		}
	}
}
