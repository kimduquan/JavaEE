package epf.workflow.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import epf.workflow.schema.Duration;
import epf.workflow.schema.ScriptProcess;
import epf.workflow.spi.ScriptProcessService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScriptProcessServiceImpl implements ScriptProcessService {

	@Override
	public Object run(final ScriptProcess scriptProcess, final boolean await, final Duration timeout) throws Error {
		try {
			final ScriptEngineManager manager = new ScriptEngineManager();
			String engineName = "";
			if(ScriptProcess.JS.equals(scriptProcess.getLanguage())) {
				engineName = "JavaScript";
			}
			else if(ScriptProcess.PYTHON.equals(scriptProcess.getLanguage())) {
				engineName = "Python";
			}
			final ScriptEngine engine = manager.getEngineByName(engineName);
			final Bindings arguments = new SimpleBindings();
			scriptProcess.getArguments().forEach((name, value) -> {
				arguments.put(name, value);
			});
			Object output = null;
			if(scriptProcess.getCode() != null) {
				output = engine.eval(scriptProcess.getCode(), arguments);
			}
			else if(scriptProcess.getSource() != null) {
				final URI sourceURI = URI.create(scriptProcess.getSource().getEndpoint().getUri());
				try(InputStream sourceStream = sourceURI.toURL().openStream()){
					try(InputStreamReader sourceReader = new InputStreamReader(sourceStream)){
						output = engine.eval(sourceReader, arguments);
					}
				}
			}
			return output;
		}
		catch(Exception ex) {
			throw new Error(ex);
		}
	}
}
