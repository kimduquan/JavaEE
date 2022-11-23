package epf.script;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.ScriptedTask;
import org.jppf.node.protocol.Task;
import epf.naming.Naming;
import epf.util.EPFException;

/**
 * @author PC
 *
 */
@Path(Naming.SCRIPT)
@ApplicationScoped
public class Script implements epf.client.script.Script {
	
	/**
	 * 
	 */
	@Inject
	private transient Engine engine;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Script.ROOT)
	@Inject
	private transient String rootFolder;

	@Override
	public Response eval(final String lang, final InputStream input, final SecurityContext security) {
		final JPPFJob job = new JPPFJob();
		final Map<String, Object> bindings = new ConcurrentHashMap<>();
		final java.nio.file.Path targetFolder = Paths.get(rootFolder, security.getUserPrincipal().getName(), lang);
		java.nio.file.Path tempFile = null;
		try {
			tempFile = Files.createTempFile(targetFolder, "", "");
			Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			final String reusableId = tempFile.getFileName().toString();
			final ScriptedTask<Object> task = new ScriptedTask<>(lang, tempFile.toFile(), reusableId, bindings);
			job.add(task);
			final String jobId = engine.getConnection().submit(job);
			engine.getConnection().awaitResults(jobId);
			final List<Task<?>> result = engine.getConnection().getResults(jobId);
			final ResponseBuilder response = Response.ok();
			if(!result.isEmpty()) {
				response.entity(result.get(0).getResult());
			}
			return response.build();
		} 
		catch (Exception e) {
			throw new EPFException(e);
		}
		finally {
			if(tempFile != null) {
				tempFile.toFile().delete();
			}
		}
	}
}
