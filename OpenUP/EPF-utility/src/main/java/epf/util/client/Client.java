/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.net.URI;
import java.util.function.Function;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

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
    	client = clients.poll(uri, buildClient);
        this.uri = uri;
        this.clients = clients;
    }

	/**
	 * @param token
	 * @return
	 */
	public Client authorization(final String token) {
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
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	if(authHeader != null) {
        	request = request.header(HttpHeaders.AUTHORIZATION, authHeader);
    	}
    	return buildRequest.apply(request);
    }
    
    /**
     * @param uri
     * @param buildTarget
     * @param buildRequest
     * @return
     */
    public Invocation.Builder request(final String uri, final Function<WebTarget, WebTarget> buildTarget, final Function<Invocation.Builder, Invocation.Builder> buildRequest) {
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	if(authHeader != null) {
        	request = request.header(HttpHeaders.AUTHORIZATION, authHeader);
    	}
    	return buildRequest.apply(request);
    }
}
