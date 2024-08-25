package epf.gateway.util;

import java.io.InputStream;
import java.net.URI;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 * 
 */
public class RequestBuilder {
	
	/**
	 * 
	 */
	private transient jakarta.ws.rs.client.Client client;
	
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
	 * @param client
	 * @param serviceUrl
	 * @param method
	 * @param headers
	 * @param uriInfo
	 * @param body
	 * @param buildForwardHeaders
	 */
	public RequestBuilder(final jakarta.ws.rs.client.Client client, final URI serviceUrl, final String method, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body, final boolean buildForwardHeaders) {
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
		WebTarget target = client.target(serviceUrl);
		target = RequestUtil.buildTarget(target, uriInfo);
		Invocation.Builder builder = target.request();
		builder = RequestUtil.buildHeaders(builder, headers, serviceUrl, buildForwardHeaders);
		response = RequestUtil.buildInvoke(builder, method, headers.getMediaType(), body);
		return response;
	}
}
