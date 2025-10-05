package epf.management.config.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.spi.ConfigSource;
import epf.util.logging.LogManager;

public class ConfigPath implements ConfigSource {
	
	private static transient final Logger LOGGER = LogManager.getLogger(ConfigPath.class.getName());
	
	private final Path path;
	
	public ConfigPath(final String path) {
		this.path = Path.of(path);
	}

	@Override
	public Set<String> getPropertyNames() {
		return new LinkedHashSet<>(Arrays.asList(path.toFile().list()));
	}

	@Override
	public String getValue(final String propertyName) {
		try {
			return Files.readString(path.resolve(propertyName));
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, propertyName, ex);
			return null;
		}
	}

	@Override
	public String getName() {
		return path.toString();
	}

}
