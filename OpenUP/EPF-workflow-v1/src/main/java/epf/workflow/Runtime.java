package epf.workflow;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import epf.naming.Naming;
import epf.util.json.ext.JsonUtil;
import epf.workflow.el.Processor;
import epf.workflow.schema.AsyncAPI;
import epf.workflow.schema.AsyncAPICall;
import epf.workflow.schema.Call;
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
import epf.workflow.schema.Set;
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
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

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
		final String endpointUri = String.format("grpc:%s:%d/%s?method=%s", grpc.getService().getHost(), grpc.getService().getPort(), grpc.getService().getName(), grpc.getMethod());
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return new ResponseBuilder().entity(JsonUtil.toJsonValue(entity));
	}
	
	private ResponseBuilder http(final Instance instance, final Task task, final HTTP http, final JsonValue input) throws Exception {
		final String endpointUri = http.getEndpoint().isLeft() ? http.getEndpoint().getLeft() : http.getEndpoint().getRight().getUri();
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return new ResponseBuilder().entity(JsonUtil.toJsonValue(entity));
	}
	
	private ResponseBuilder openapi(final Instance instance, final Task task, final OpenAPI openapi, final JsonValue input) throws Exception {
		final String endpointUri = String.format("rest-openapi:%s#%s", openapi.getDocument().getEndpoint().getUri(), openapi.getOperationId());
		final Object body = JsonUtil.asValue(input);
		final Object entity = producer.requestBody(endpointUri, body);
		return new ResponseBuilder().entity(JsonUtil.toJsonValue(entity));
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
		return null;
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
		return null;
	}
	
	private ResponseBuilder run(final Instance instance, final Task task, final Run run, final JsonValue input) throws Exception {
		return null;
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
