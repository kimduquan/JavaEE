package epf.client;

import java.io.IOException;
import java.net.URI;
import java.util.function.Function;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.util.ssl.DefaultHostnameVerifier;
import epf.util.ssl.DefaultSSLContext;

public class RestClient implements AutoCloseable, ClientRequestFilter {
	
	private static SSLContext sslContext;
	
	private RestClientBuilder builder;
	private String authHeader;
	
	public RestClient(URI uri, Function<RestClientBuilder, RestClientBuilder> build) {
		if(sslContext == null) {
			sslContext = DefaultSSLContext.build();
		}
		builder = RestClientBuilder
				.newBuilder()
				.baseUri(uri)
				.hostnameVerifier(new DefaultHostnameVerifier())
				.sslContext(sslContext);
		builder = build.apply(builder);
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		if(authHeader != null) {
			requestContext
			.getHeaders()
			.putSingle(
					HttpHeaders.AUTHORIZATION, 
					builder.toString()
					);
		}
	}

	@Override
	public void close() throws Exception {
	}
	
	public RestClient authorization(String token) {
		StringBuilder tokenHeader = new StringBuilder();
    	tokenHeader.append("Bearer ");
    	tokenHeader.append(token);
    	authHeader = tokenHeader.toString();
		return this;
	}

	public <T> T build(Class<T> cls) {
		return builder.register(this).build(cls);
	}
}
