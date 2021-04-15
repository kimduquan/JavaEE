/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.sse.SseEventSource;

/**
 *
 * @author FOXCONN
 */
public class Client implements AutoCloseable {

	/**
	 * 
	 */
	private transient final URI uri;
    /**
     * 
     */
    private transient String authHeader;
    /**
     * 
     */
    private transient final javax.ws.rs.client.Client client;
    /**
     * 
     */
    private transient final ClientQueue clients;
    
    /**
     * @param clients
     * @param uri
     * @param buildClient
     */
    public Client(final ClientQueue clients, final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient) {
    	Objects.requireNonNull(clients);
    	Objects.requireNonNull(uri);
    	Objects.requireNonNull(buildClient);
    	client = clients.poll(uri, buildClient);
        this.uri = uri;
        this.clients = clients;
    }

	/**
	 * @return the uri
	 */
	protected URI getUri() {
		return uri;
	}

	/**
	 * @return the authHeader
	 */
	protected String getAuthHeader() {
		return authHeader;
	}

	/**
	 * @return the client
	 */
	protected javax.ws.rs.client.Client getClient() {
		return client;
	}

	/**
	 * @return the clients
	 */
	protected ClientQueue getClients() {
		return clients;
	}

	/**
	 * @param token
	 * @return
	 */
	public Client authorization(final String token) {
		Objects.requireNonNull(token);
		final StringBuilder tokenHeader = new StringBuilder();
    	authHeader = tokenHeader.append("Bearer ").append(token).toString();
		return this;
	}

    @Override
    public void close() throws Exception {
        clients.add(uri, client);
    }
    
    /**
     * @param buildTarget
     * @param buildRequest
     * @return
     */
    public Invocation.Builder request(final Function<WebTarget, WebTarget> buildTarget, final Function<Invocation.Builder, Invocation.Builder> buildRequest) {
    	Objects.requireNonNull(buildTarget);
    	Objects.requireNonNull(buildRequest);
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	if(authHeader != null) {
        	request = request.header(HttpHeaders.AUTHORIZATION, authHeader);
    	}
    	return buildRequest.apply(request);
    }
    
    /**
     * @param buildTarget
     * @return
     */
    public SseEventSource stream(final Function<WebTarget, WebTarget> buildTarget, final Function<SseEventSource.Builder, SseEventSource.Builder> buildEvent) {
    	Objects.requireNonNull(buildTarget);
    	Objects.requireNonNull(buildEvent);
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	SseEventSource.Builder builder = SseEventSource.target(target);
    	builder = buildEvent.apply(builder);
    	return builder.build();
    }
}
