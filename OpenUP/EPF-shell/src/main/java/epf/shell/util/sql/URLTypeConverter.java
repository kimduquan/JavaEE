package epf.shell.util.sql;

import java.net.URL;
import picocli.CommandLine.ITypeConverter;

/**
 * 
 */
public class URLTypeConverter implements ITypeConverter<URL> {

	@Override
	public URL convert(final String value) throws Exception {
		return new URL(value);
	}
}
