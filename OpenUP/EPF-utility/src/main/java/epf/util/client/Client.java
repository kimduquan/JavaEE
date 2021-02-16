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

	private URI uri;
    private String authHeader;
    private javax.ws.rs.client.Client client;
    private ClientQueue clients;
    
    public Client(ClientQueue clients, URI uri, Function<ClientBuilder, ClientBuilder> buildClient) {
    	client = clients.poll(uri, buildClient);
        this.uri = uri;
        this.clients = clients;
    }

	public Client authorization(String token) {
		StringBuilder tokenHeader = new StringBuilder();
    	tokenHeader.append("Bearer ");
    	tokenHeader.append(token);
    	authHeader = tokenHeader.toString();
		return this;
	}

    @Override
    public void close() throws Exception {
        clients.add(uri, client);
        uri = null;
        client = null;
    }
    
    public Invocation.Builder request(Function<WebTarget, WebTarget> buildTarget, Function<Invocation.Builder, Invocation.Builder> buildRequest) {
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	request = request.header(HttpHeaders.AUTHORIZATION, authHeader);
    	return buildRequest.apply(request);
    }
    
    public Invocation.Builder request(String uri, Function<WebTarget, WebTarget> buildTarget, Function<Invocation.Builder, Invocation.Builder> buildRequest) {
    	WebTarget target = client.target(uri);
    	target = buildTarget.apply(target);
    	Invocation.Builder request = target.request();
    	request = request.header(HttpHeaders.AUTHORIZATION, authHeader);
    	return buildRequest.apply(request);
    }
}
