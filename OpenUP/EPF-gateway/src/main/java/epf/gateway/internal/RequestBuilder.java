package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * 
 */
public class RequestBuilder {
	
	/**
	 *
	 */
	private transient final Client client;
	
	/**
	 *
	 */
	private final URI serviceUrl;
	/**
	 *
	 */
	private transient final JsonWebToken jwt;
	/**
	 *
	 */
	private transient final HttpHeaders headers;
	/**
	 *
	 */
	private transient final UriInfo uriInfo;
	/**
	 *
	 */
	private transient final Request request;
	/**
	 *
	 */
	private transient final InputStream body;
	
	/**
	 *
	 */
	private transient boolean useTargetHost;
	
	/**
	 * @param client
	 * @param serviceUrl
	 * @param jwt
	 * @param request
	 * @param headers
	 * @param uriInfo
	 * @param body
	 */
	public RequestBuilder(final Client client, final URI serviceUrl, final JsonWebToken jwt, final Request request, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body) {
		this.client = client;
		this.serviceUrl = serviceUrl;
		this.jwt = jwt;
		this.headers = headers;
		this.uriInfo = uriInfo;
		this.request = request;
		this.body = body;
		
	}
	
	/**
	 * @return
	 */
	public RequestBuilder useTargetHost() {
		useTargetHost = true;
		return this;
	}
	
	/**
	 * @return
	 */
	public CompletionStage<Response> build(){
		final CompletionStage<Response> builtRequest = RequestUtil.buildRequest(client, serviceUrl, jwt, headers, uriInfo, request, body, useTargetHost);
		return ResponseUtil.buildResponse(builtRequest, uriInfo.getBaseUri());
	}
}
