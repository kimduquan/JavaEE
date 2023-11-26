package epf.client.util;

import java.io.InputStream;
import java.net.URI;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 */
public class RequestBuilder {
	
	/**
	 *
	 */
	private transient final ClientBuilderUtil clientBuilder;
	
	/**
	 * 
	 */
	private transient javax.ws.rs.client.Client client;
	
	/**
	 *
	 */
	private final URI serviceUrl;
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
	private transient final String method;
	/**
	 *
	 */
	private transient final InputStream body;
	
	/**
	 * 
	 */
	private final boolean buildForwardHeaders;
	
	/**
	 * @param clientBuilder
	 * @param serviceUrl
	 * @param method
	 * @param headers
	 * @param uriInfo
	 * @param body
	 * @param buildForwardHeaders
	 */
	public RequestBuilder(final ClientBuilderUtil clientBuilder, final URI serviceUrl, final String method, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body, final boolean buildForwardHeaders) {
		this.clientBuilder = clientBuilder;
		this.serviceUrl = serviceUrl;
		this.headers = headers;
		this.uriInfo = uriInfo;
		this.method = method;
		this.body = body;
		this.buildForwardHeaders = buildForwardHeaders;
	}
	
	/**
	 * @param client
	 * @param serviceUrl
	 * @param method
	 * @param headers
	 * @param uriInfo
	 * @param body
	 * @param buildForwardHeaders
	 */
	public RequestBuilder(final javax.ws.rs.client.Client client, final URI serviceUrl, final String method, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body, final boolean buildForwardHeaders) {
		this.clientBuilder = null;
		this.client = client;
		this.serviceUrl = serviceUrl;
		this.headers = headers;
		this.uriInfo = uriInfo;
		this.method = method;
		this.body = body;
		this.buildForwardHeaders = buildForwardHeaders;
	}
	
	/**
	 * @return
	 */
	public Response build(){
		Response response;
		if(clientBuilder != null) {
			client = clientBuilder.newClient(serviceUrl, b -> b);
			response = RequestUtil.buildRequest(client, serviceUrl, headers, uriInfo, method, body, buildForwardHeaders);
		}
		else {
			response = RequestUtil.buildRequest(client, serviceUrl, headers, uriInfo, method, body, buildForwardHeaders);
		}
		return response;
	}
}
