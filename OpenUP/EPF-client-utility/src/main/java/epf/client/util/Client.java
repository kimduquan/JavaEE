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
     *
     */
    private transient char[] authToken;
    
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
	 * @return
	 */
	protected char[] getAuthToken() {
		return authToken;
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
	public Client authorization(final char[] token) {
		Objects.requireNonNull(token);
		authToken = token;
		return this;
	}
	
	/**
	 * @param header
	 * @return
	 */
	public Client authorizationHeader(final String header) {
		Objects.requireNonNull(header);
		authToken = header.substring("Bearer ".length()).toCharArray();
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

    @Override
    public void close() throws Exception {
    	authToken = null;
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
    	if(authToken != null) {
    		final StringBuilder builder = new StringBuilder();
    		builder.append("Bearer ").append(authToken);
        	request = request.header(HttpHeaders.AUTHORIZATION, builder.toString());
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
