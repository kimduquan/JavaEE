/**
 * 
 */
package epf.shell.file;

import java.nio.file.Path;
import epf.util.file.PathUtil;
import picocli.CommandLine.ITypeConverter;

/**
 * @author PC
 *
 */
public class PathTypeConverter implements ITypeConverter<Path> {

	@Override
	public Path convert(final String value) throws Exception {
		return PathUtil.of(value);
	}

}
