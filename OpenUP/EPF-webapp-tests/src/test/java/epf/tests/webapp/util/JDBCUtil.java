package epf.tests.webapp.util;

import java.nio.file.Path;
import java.util.List;
import epf.naming.Naming;

public class JDBCUtil {

	public static List<String> executeBatch(ProcessBuilder builder, Path in, Path out, String url, String username, String password, Path file) throws Exception {
		builder = ShellUtil.command(builder, Naming.UTILITY, "jdbc", "-url", url, "-u", username, "-f", file.toAbsolutePath().toString(), "-p");
		Process process = ShellUtil.waitFor(builder, in, password);
		List<String> lines = ShellUtil.getOutput(out);
		lines.stream().forEach(System.out::println);
		process.destroyForcibly();
		return lines;
	}
}
