/**
 * 
 */
package epf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PC
 *
 */
public interface ProcessUtil {

	/**
	 * @param builder
	 * @param command
	 * @return
	 */
	static ProcessBuilder command(final ProcessBuilder builder, final String...command) {
		final List<String> cmd = new ArrayList<>();
		cmd.add("bash");
		cmd.addAll(Arrays.asList(command));
		return builder.command(cmd);
	}
}
