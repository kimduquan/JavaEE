package epf.transaction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.client.Request;
import epf.naming.Naming;
import epf.util.io.InputStreamUtil;
import epf.util.logging.LogManager;

/**
 * 
 */
@Path(Naming.TRANSACTION)
@ApplicationScoped
public class Transaction implements epf.client.transaction.Transaction {
	
	/**
	 *
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(Transaction.class.getName());

	@Override
	@LRA
	public CompletionStage<Response> commit(
			final HttpHeaders headers, 
			final UriInfo uriInfo, 
			final javax.ws.rs.core.Request request,
            final List<Request> requests) throws Exception {
		CompletionStage<epf.client.Response> stage = null;
		final List<CompletionStage<epf.client.Response>> responses = new ArrayList<>();
		for(Request req : requests) {
			final CompletionStage<epf.client.Response> response = invokeRequest(req);
			responses.add(response);
			if(stage == null) {
				stage = response;
			}
			else {
				stage = stage.thenCombineAsync(response, (firstRes, secondRes) -> secondRes);
			}
		}
		if(stage != null) {
			return stage.thenApply(response -> Response.ok(buildResponses(responses)).header(LRA.LRA_HTTP_CONTEXT_HEADER, headers.getHeaderString(LRA.LRA_HTTP_CONTEXT_HEADER)).build());
		}
		throw new BadRequestException();
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private CompletionStage<epf.client.Response> invokeRequest(final Request request) throws Exception {
		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target(request.getTarget());
		if(request.getMatrixParams() != null) {
			for(Entry<String, Object> matrixParam : request.getMatrixParams().entrySet()) {
				target = target.matrixParam(matrixParam.getKey(), matrixParam.getValue());
			}
		}
		target = target.path(request.getPath());
		if(request.getQueryParams() != null) {
			for(Entry<String, Object> queryParam : request.getQueryParams().entrySet()) {
				target = target.queryParam(queryParam.getKey(), queryParam.getValue());
			}
		}
		Builder builder = target.request();
		if(request.getHeaders() != null) {
			for(Entry<String, Object> header : request.getHeaders().entrySet()) {
				builder = builder.header(header.getKey(), header.getValue());
			}
		}
		if(request.getMethod() != null) {
			if(request.getEntity() != null && request.getMediaType() != null) {
				final InputStream entity = Base64.getDecoder().wrap(new ByteArrayInputStream(request.getEntity().getBytes("UTF-8")));
				return builder.rx().method(request.getMethod(), Entity.entity(entity, request.getMediaType())).thenApply(this::buildResponse);
			}
			else {
				return builder.rx().method(request.getMethod()).thenApply(this::buildResponse);
			}
		}
		throw new BadRequestException();
	}
	
	/**
	 * @param res
	 * @return
	 */
	private epf.client.Response buildResponse(final Response res) {
		final epf.client.Response response = new epf.client.Response();
		final InputStream entity = Base64.getDecoder().wrap(res.readEntity(InputStream.class));
		try {
			response.setEntity(InputStreamUtil.readString(entity, "UTF-8", System.lineSeparator()));
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Transaction][commit]", e);
		}
		response.setHeaders(new HashMap<>());
		for(Entry<String, List<Object>> header : res.getHeaders().entrySet()) {
			response.getHeaders().put(header.getKey(), header.getValue());
		}
		response.setStatus(res.getStatus());
		return response;
	}
	
	/**
	 * @param responses
	 * @return
	 */
	private List<epf.client.Response> buildResponses(final List<CompletionStage<epf.client.Response>> responses){
		return responses.stream().map(r -> {
			try {
				return r.toCompletableFuture().get();
			} 
			catch (Exception e) {
				return null;
			}
		}).collect(Collectors.toList());
	}

	@Override
	@Compensate
	public Response rollback() throws Exception {
		return Response.ok().build();
	}
}
