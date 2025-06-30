package epf.workflow.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import epf.workflow.schema.ContainerProcess;
import epf.workflow.schema.Duration;
import epf.workflow.schema.Error;
import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.RuntimeError;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.util.DurationUtil;
import epf.workflow.schema.util.WorkflowUtil;
import epf.workflow.spi.ContainerProcessService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContainerProcessServiceImpl implements ContainerProcessService {

	@Override
	public ProcessResult run(final ContainerProcess containerProcess, final RuntimeExpressionArguments arguments, final boolean await, final Duration timeout) throws Error {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			final Path tempDir = Files.createTempDirectory(null);
			final Path stderr = Files.createTempFile(tempDir, null, null);
			final Path stdout = Files.createTempFile(tempDir, null, null);
			final Path tempEnvFile = Files.createTempFile(tempDir, null, null);
			final List<String> commands = new ArrayList<>(Arrays.asList("docker", "container", "run", "--attach"));
			if(containerProcess.getName() != null) {
				commands.add("--name");
				commands.add(containerProcess.getName());
			}
			else {
				final String name = WorkflowUtil.getName(
						arguments.getWorkflow().getDefinition().getDocument().getName(), 
						arguments.getWorkflow().getId(), 
						arguments.getWorkflow().getDefinition().getDocument().getNamespace(),
						arguments.getTask().getName());
				commands.add("--name");
				commands.add(name);
			}
			if(containerProcess.getPorts() != null) {
				for(Map.Entry<String, String> entry : containerProcess.getPorts().entrySet()) {
					commands.add("-p");
					commands.add(entry.getKey() + ":" + entry.getValue());
				}
			}
			if(containerProcess.getVolumes() != null) {
				for(Map.Entry<String, String> entry : containerProcess.getVolumes().entrySet()) {
					commands.add("-v");
					commands.add(entry.getKey() + ":" + entry.getValue());
				}
			}
			if(containerProcess.getEnvironment() != null) {
				final StringBuilder env = new StringBuilder();
				for(Map.Entry<String, String> entry : containerProcess.getEnvironment().entrySet()) {
					env.append(entry.getKey());
					env.append('=');
					env.append(entry.getValue());
					env.append('\n');
				}
				Files.writeString(tempEnvFile, env.toString());
				commands.add("--env-file");
				commands.add(tempEnvFile.toAbsolutePath().toString());
			}
			if(containerProcess.getLifetime() != null) {
				switch(containerProcess.getLifetime().getCleanup()) {
					case always:
						commands.add("--rm");
						break;
					case eventually:
						break;
					case never:
						break;
					default:
						break;
				}
			}
			commands.add(containerProcess.getImage());
			if(containerProcess.getCommand() != null) {
				commands.add(containerProcess.getCommand());
			}
			builder = builder
					.redirectError(stderr.toFile())
					.redirectOutput(stdout.toFile())
					.command(commands);
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
			Files.delete(tempEnvFile);
			Files.delete(tempDir);
			return processResult;
		}
		catch(Exception ex) {
			throw new RuntimeError(ex);
		}
	}
}
