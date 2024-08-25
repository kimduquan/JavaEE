/**
 * 
 */
package epf.shell.util.file;

import java.nio.file.Path;
import epf.file.util.PathUtil;
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
