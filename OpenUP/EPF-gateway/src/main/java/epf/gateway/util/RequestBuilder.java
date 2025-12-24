package epf.gateway.util;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

public class RequestBuilder {
	
	private transient final dev.openfeature.sdk.Client feature;
	private transient final jakarta.ws.rs.client.Client client;
	private final URI serviceUrl;
	private transient final HttpHeaders headers;
	private transient final UriInfo uriInfo;
	private transient final String method;
	private transient final InputStream body;
	private final boolean buildForwardHeaders;
	private final Optional<String> organizationId;
	
	public RequestBuilder(final dev.openfeature.sdk.Client feature, final jakarta.ws.rs.client.Client client, final URI serviceUrl, final String method, final HttpHeaders headers, final UriInfo uriInfo, final InputStream body, final boolean buildForwardHeaders, final Optional<String> organizationId) {
		this.feature = feature;
		this.client = client;
		this.serviceUrl = serviceUrl;
		this.headers = headers;
		this.uriInfo = uriInfo;
		this.method = method;
		this.body = body;
		this.buildForwardHeaders = buildForwardHeaders;
		this.organizationId = organizationId;
	}
	
	public Response build(){
		Response response;
		WebTarget target = client.target(serviceUrl);
		target = RequestUtil.buildTarget(target, uriInfo);
		Invocation.Builder builder = target.request();
		builder = RequestUtil.buildHeaders(builder, headers, serviceUrl, feature, buildForwardHeaders, organizationId);
		response = RequestUtil.buildInvoke(builder, method, headers.getMediaType(), body);
		return response;
	}
}
