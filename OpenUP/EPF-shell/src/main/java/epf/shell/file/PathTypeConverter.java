/**
 * 
 */
package epf.shell.file;

import java.nio.file.Path;
import picocli.CommandLine.ITypeConverter;

/**
 * @author PC
 *
 */
public class PathTypeConverter implements ITypeConverter<Path> {

	@Override
	public Path convert(final String value) throws Exception {
		return Path.of(value);
	}

}
