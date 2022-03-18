/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.util;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.sse.SseEventSource;

import epf.naming.Naming;

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
    private transient Optional<String> authHeader = Optional.empty();
    /**
     * 
     */
    private transient final javax.ws.rs.client.Client rsClient;
    /**
     * 
     */
    private transient final ClientQueue clients;
    
    /**
     * 
     */
    private transient Optional<String> tenant = Optional.empty();
    
    /**
     * @param clients
     * @param uri
     * @param buildClient
     */
    public Client(final ClientQueue clients, final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient) {
    	Objects.requireNonNull(clients);
    	Objects.requireNonNull(uri);
    	rsClient = clients.poll(uri, buildClient);
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
	protected Optional<String> getAuthHeader() {
		return authHeader;
	}

	/**
	 * @return the client
	 */
	protected javax.ws.rs.client.Client getClient() {
		return rsClient;
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
    	authHeader = Optional.of(tokenHeader.append("Bearer ").append(token).toString());
		return this;
	}
	
	/**
	 * @param tenant
	 * @return
	 */
	public Client tenant(final String tenant) {
		this.tenant = Optional.ofNullable(tenant);
		return this;
	}
	
	/**
	 * @param header
	 * @return
	 */
	public Client authorizationHeader(final String header) {
		authHeader = Optional.of(header);
		return this;
	}

    @Override
    public void close() throws Exception {
    	authHeader = Optional.empty();
        clients.add(uri, rsClient);
    }
    
    /**
     * @param buildTarget
     * @param buildRequest
     * @return
     */
    public Invocation.Builder request(final Function<WebTarget, WebTarget> buildTarget, final Function<Invocation.Builder, Invocation.Builder> buildRequest) {
    	Objects.requireNonNull(buildTarget);
    	Objects.requireNonNull(buildRequest);
    	WebTarget target = rsClient.target(uri);
    	if(tenant.isPresent()) {
    		target = target.matrixParam(Naming.Management.TENANT, tenant.get());
    	}
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	if(authHeader.isPresent()) {
        	request = request.header(HttpHeaders.AUTHORIZATION, authHeader.get());
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
    	WebTarget target = rsClient.target(uri);
    	target = buildTarget.apply(target);
    	SseEventSource.Builder builder = SseEventSource.target(target);
    	builder = buildEvent.apply(builder);
    	return builder.build();
    }
}
