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
		String trimValue = value;
		if(trimValue.startsWith("\"")) {
			trimValue = trimValue.substring(1);
		}
		if(trimValue.endsWith("\"")) {
			trimValue = trimValue.substring(0, trimValue.length() - 1);
		}
		return PathUtil.of(trimValue);
	}

}
