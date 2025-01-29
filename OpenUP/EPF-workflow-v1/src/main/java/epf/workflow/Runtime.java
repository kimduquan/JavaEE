package epf.workflow;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.InputStream;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import epf.event.schema.Event;
import epf.naming.Naming;
import epf.workflow.el.Processor;
import epf.workflow.schema.AsyncAPI;
import epf.workflow.schema.AsyncAPICall;
import epf.workflow.schema.Call;
import epf.workflow.schema.ContainerProcess;
import epf.workflow.schema.Emit;
import epf.workflow.schema.Export;
import epf.workflow.schema.For;
import epf.workflow.schema.Fork;
import epf.workflow.schema.GRPC;
import epf.workflow.schema.GRPCCall;
import epf.workflow.schema.HTTP;
import epf.workflow.schema.HTTPCall;
import epf.workflow.schema.Listen;
import epf.workflow.schema.OpenAPI;
import epf.workflow.schema.OpenAPICall;
import epf.workflow.schema.Raise;
import epf.workflow.schema.Run;
import epf.workflow.schema.ScriptProcess;
import epf.workflow.schema.Set;
import epf.workflow.schema.ShellProcess;
import epf.workflow.schema.Switch;
import epf.workflow.schema.Task;
import epf.workflow.schema.Try;
import epf.workflow.schema.Wait;
import epf.workflow.schema.Workflow;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path(Naming.WORKFLOW)
public class Runtime {
	
	@Inject
	transient Persistence persistence;
	
	@Inject
	transient Validator validator;
	
	@Inject
	transient Transformer transformer;
	
	@Inject
	transient Cache cache;
	
	@Inject
	transient Integration integration;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@LRA(value = Type.NESTED, end = false)
	@RunOnVirtualThread
	public Response start(
			@QueryParam("workflow")
			final String name, 
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI id,
			final JsonValue input) throws Exception {
		final Workflow workflow = persistence.find(name, version);
		if(workflow != null) {
			if(validator.validate(input, workflow)) {
				final JsonValue context = transformer.transform(input, workflow.getInput());
				final Instance instance = new Instance();
				instance.setId(id);
				instance.setWorkflow(workflow);
				cache.put(id, instance);
				ResponseBuilder builder = new ResponseBuilder();
				builder = builder.entity(context);
				for(Map<String, Task> do_ : workflow.getDo_()) {
					for(Map.Entry<String, Task> doTask : do_.entrySet()) {
						final String taskName = doTask.getKey();
						builder = builder.link(Naming.WORKFLOW, HttpMethod.PUT, taskName);
					}
				}
				return builder.build();
			}
			throw new BadRequestException();
		}
		throw new NotFoundException();
	}
	
	private ResponseBuilder asyncapi(final Instance instance, final Task task, final AsyncAPI asyncapi, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder grpc(final Instance instance, final Task task, final GRPC grpc, final JsonValue input) throws Exception {
		final Object entity = integration.grpc(instance, task, grpc, input);
		return new ResponseBuilder().entity(entity);
	}
	
	private ResponseBuilder http(final Instance instance, final Task task, final HTTP http, final JsonValue input) throws Exception {
		final Object entity = integration.http(instance, task, http, input);
		return new ResponseBuilder().entity(entity);
	}
	
	private ResponseBuilder openapi(final Instance instance, final Task task, final OpenAPI openapi, final JsonValue input) throws Exception {
		final Object entity = integration.openapi(instance, task, openapi, input);
		return new ResponseBuilder().entity(entity);
	}
	
	private ResponseBuilder container(final Instance instance, final Task task, final ContainerProcess container, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder script(final Instance instance, final Task task, final ScriptProcess script, final JsonValue input) throws Exception {
		final ScriptEngineManager manager = new ScriptEngineManager();
		final Optional<ScriptEngineFactory> factory =  manager.getEngineFactories().stream().filter(engine -> engine.getLanguageName().equals(script.getLanguage())).findFirst();
		if(factory.isPresent()) {
			final ScriptEngine engine = factory.get().getScriptEngine();
			final SimpleBindings bindings = new SimpleBindings();
			script.getArguments().forEach((name, value) -> {
				bindings.put(name.toString(), value);
			});
			Object entity = null;
			if(script.getCode() != null) {
				entity = engine.eval(script.getCode(), bindings);
			}
			else if(script.getSource() != null) {
				try(InputStream stream = URI.create(script.getSource().getEndpoint().getUri()).toURL().openStream();
					InputStreamReader reader = new InputStreamReader(stream)) {
					entity = engine.eval(reader, bindings);
				}
			}
			return new ResponseBuilder().entity(entity);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder shell(final Instance instance, final Task task, final ShellProcess shell, final JsonValue input) throws Exception {
		final Map<String, String> environment = new LinkedHashMap<>();
		shell.getEnvironment().forEach((name, value) -> {
			environment.put(name.toString(), String.valueOf(value));
		});
		final List<String> commands = new LinkedList<>();
		commands.add(shell.getCommand());
		shell.getArguments().forEach((name, value) -> {
			commands.add(name.toString());
		});
		final ProcessBuilder builder = new ProcessBuilder();
		builder.command(commands).environment().putAll(environment);
		builder.start();
		return new ResponseBuilder();
	}
	
	private ResponseBuilder workflow(final Instance instance, final Task task, final Workflow workflow, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder call(final Instance instance, final Task task, final Call<?> call, final JsonValue input) throws Exception {
		if(call instanceof AsyncAPICall) {
			final AsyncAPI asyncapi = ((AsyncAPICall) call).getWith();
			return asyncapi(instance, task, asyncapi, input);
		}
		else if(call instanceof GRPCCall) {
			final GRPC grpc = ((GRPCCall) call).getWith();
			return grpc(instance, task, grpc, input);
		}
		else if(call instanceof HTTPCall) {
			final HTTP http = ((HTTPCall) call).getWith();
			return http(instance, task, http, input);
		}
		else if(call instanceof OpenAPICall) {
			final OpenAPI openapi = ((OpenAPICall) call).getWith();
			return openapi(instance, task, openapi, input);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder do_(final Instance instance, final Task task, epf.workflow.schema.Do do_, final JsonValue input) throws Exception {
		ResponseBuilder builder = new ResponseBuilder();
		builder = builder.entity(input);
		for(Map<String, Task> d : do_.getDo_()) {
			for(Map.Entry<String, Task> doTask : d.entrySet()) {
				final String taskName = doTask.getKey();
				builder = builder.link(Naming.WORKFLOW, HttpMethod.PUT, taskName);
			}
		}
		return builder;
	}
	
	private ResponseBuilder emit(final Instance instance, final Task task, final Emit emit, final JsonValue input) throws Exception {
		final Event event = new Event();
		event.setData(emit.getEmit().getEvent().getData());
		event.setDataContentType(emit.getEmit().getEvent().getDatacontenttype());
		event.setId(emit.getEmit().getEvent().getId());
		event.setSource(emit.getEmit().getEvent().getSource());
		event.setSubject(emit.getEmit().getEvent().getSubject());
		event.setTime(emit.getEmit().getEvent().getTime());
		event.setType(emit.getEmit().getEvent().getType());
		return new ResponseBuilder().entity(event).link(Naming.EVENT, HttpMethod.PUT, Naming.EVENT);
	}
	
	private ResponseBuilder export(final Instance instance, final Task task, final Export export, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder for_(final Instance instance, final Task task, final For for_, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder fork(final Instance instance, final Task task, final Fork fork, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder listen(final Instance instance, final Task task, final Listen listen, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder raise(final Instance instance, final Task task, final Raise raise, final JsonValue input) throws Exception {
		final WorkflowError workflowError = new WorkflowError();
		epf.workflow.schema.Error error = null;
		if(raise.getRaise().getError().isLeft()) {
			error = instance.getWorkflow().getUse().getErrors().get(raise.getRaise().getError().getLeft()).clone();
		}
		else if(raise.getRaise().getError().isRight()) {
			error = raise.getRaise().getError().getRight().clone();
		}
		error.setInstance(instance.getId().toString());
		workflowError.setError(error);
		throw workflowError;
	}
	
	private ResponseBuilder run(final Instance instance, final Task task, final Run run, final JsonValue input) throws Exception {
		if(run.getRun().getContainer() != null) {
			return container(instance, task, run.getRun().getContainer(), input);
		}
		else if(run.getRun().getScript() != null) {
			return script(instance, task, run.getRun().getScript(), input);
		}
		else if(run.getRun().getShell() != null) {
			return shell(instance, task, run.getRun().getShell(), input);
		}
		else if(run.getRun().getWorkflow() != null) {
			return workflow(instance, task, run.getRun().getWorkflow(), input);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder set(final Instance instance, final Task task, final Set set, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder switch_(final Instance instance, final Task task, final Switch switch_, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder try_(final Instance instance, final Task task, final Try try_, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder wait(final Instance instance, final Task task, final Wait wait, final JsonValue input) throws Exception {
		return null;
	}
	
	private ResponseBuilder doTask(final Instance instance, final Task task, final JsonValue input) throws Exception {
		if(task.getCall() != null) {
			return call(instance, task, task.getCall(), input);
		}
		else if(task.getDo_() != null) {
			return do_(instance, task, task.getDo_(), input);
		}
		else if(task.getEmit() != null) {
			return emit(instance, task, task.getEmit(), input);
		}
		else if(task.getExport() != null) {
			return export(instance, task, task.getExport(), input);
		}
		else if(task.getFor_() != null) {
			return for_(instance, task, task.getFor_(), input);
		}
		else if(task.getFork() != null) {
			return fork(instance, task, task.getFork(), input);
		}
		else if(task.getListen() != null) {
			return listen(instance, task, task.getListen(), input);
		}
		else if(task.getRaise() != null) {
			return raise(instance, task, task.getRaise(), input);
		}
		else if(task.getRun() != null) {
			return run(instance, task, task.getRun(), input);
		}
		else if(task.getSet() != null) {
			return set(instance, task, task.getSet(), input);
		}
		else if(task.getSwitch_() != null) {
			return switch_(instance, task, task.getSwitch_(), input);
		}
		else if(task.getTry_() != null) {
			return try_(instance, task, task.getTry_(), input);
		}
		else if(task.getWait() != null) {
			return wait(instance, task, task.getWait(), input);
		}
		throw new BadRequestException();
	}
	
	@Path("{name}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	public Response Do(@PathParam("name") 
					final String name, 
					@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
					final URI id,
					final JsonValue input) throws Exception {
		final Instance instance = cache.get(id);
		if(instance != null) {
			final Workflow workflow = instance.getWorkflow();
			final Optional<Map<String, Task>> do_ = workflow.getDo_().stream().filter(map -> map.containsKey(name)).findFirst();
			if(do_.isPresent()) {
				final Task task = do_.get().get(name);
				if(task.getIf_() != null) {
					final Processor processor = new Processor(input);
					final Boolean b = (Boolean) processor.eval(task.getIf_());
					if(b) {
						return doTask(instance, task, input).build();
					}
				}
				else {
					return doTask(instance, task, input).build();
				}
			}
			throw new BadRequestException();
		}
		throw new NotFoundException();
	}
	
	@Complete
	@Compensate
	@AfterLRA
	@RunOnVirtualThread
	@DELETE
	public Response end() throws Exception {
		return Response.ok().build();
	}
}
