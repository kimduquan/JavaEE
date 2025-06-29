package epf.workflow.service;

import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.ShellProcess;
import epf.workflow.schema.util.DurationUtil;
import epf.workflow.spi.ShellProcessService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.ProcessResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShellProcessServiceImpl implements ShellProcessService {

	@Override
	public ProcessResult run(final ShellProcess shellProcess, final boolean await, final Duration timeout) throws Error {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			final Path tempDir = Files.createTempDirectory(null);
			final Path stderr = Files.createTempFile(tempDir, null, null);
			final Path stdout = Files.createTempFile(tempDir, null, null);
			final List<String> commands = new ArrayList<>();
			commands.add(shellProcess.getCommand());
			shellProcess.getArguments().forEach((name, value) -> {
				if(value != null) {
					final String command = String.format("%s=%s", name, value);
					commands.add(command);
				}
				else {
					commands.add(name);
				}
			});
			builder = builder
					.redirectError(stderr.toFile())
					.redirectOutput(stdout.toFile())
					.command(commands);
			builder.environment().putAll(shellProcess.getEnvironment());
			final Process process = builder.start();
			int exitCode;
			if(await) {
				if(timeout != null) {
					final TimeUnit timeUnit = DurationUtil.getTimeUnit(timeout);
					final long time = DurationUtil.getTime(timeout, timeUnit);
					process.waitFor(time, timeUnit);
					exitCode = process.exitValue();
				}
				else {
					exitCode = process.waitFor();
				}
			}
			else {
				exitCode = process.exitValue();
			}
			final ProcessResult processResult = new ProcessResult();
			processResult.setCode(exitCode);
			processResult.setStderr(Files.readString(stderr));
			processResult.setStdout(Files.readString(stdout));
			Files.delete(stderr);
			Files.delete(stdout);
			Files.delete(tempDir);
			return processResult;
		}
		catch(Exception ex) {
			throw new RuntimeError(ex);
		}
	}
}
