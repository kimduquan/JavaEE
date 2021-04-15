/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import epf.util.ssl.DefaultHostnameVerifier;
import epf.util.ssl.SSLContextHelper;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class ClientQueue {

	/**
	 * 
	 */
	private transient final Map<String, Queue<Client>> clients;
    /**
     * 
     */
    private transient SSLContext context;
    
    /**
     * 
     */
    public ClientQueue() {
        clients = new ConcurrentHashMap<>();
    }
	
	/**
	 * @return the clients
	 */
	protected Map<String, Queue<Client>> getClients() {
		return clients;
	}

	/**
	 * @return the context
	 */
	protected SSLContext getContext() {
		return context;
	}
    
    /**
     * 
     */
    @PostConstruct
    public void initialize(){
        context = SSLContextHelper.build();
    }
    
    /**
     * 
     */
    @PreDestroy
    public void close(){
        clients.forEach((name, queue) -> {
            queue.forEach(Client::close);
            queue.clear();
        });
        clients.clear();
    }
    
    /**
     * @param context
     * @return
     */
    protected static ClientBuilder newBuilder(final SSLContext context){
    	return ClientBuilder
    			.newBuilder()
    			.sslContext(context)
                .hostnameVerifier(new DefaultHostnameVerifier());
    }
    
    /**
     * @param uri
     * @param buildClient
     * @return
     */
    public Client poll(final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient){
    	Objects.requireNonNull(uri);
    	Objects.requireNonNull(buildClient);
    	final Queue<Client> pool = clients.computeIfAbsent(
                uri.toString(), 
                key -> {
                    Queue<Client> queue = new ConcurrentLinkedQueue<>();
                    Client client = buildClient.apply(newBuilder(context)).build();
                    queue.add(client);
                    return queue;
                }
        );
        if(pool.isEmpty()){
        	final Client client = buildClient.apply(newBuilder(context)).build();
            pool.add(client);
        }
        return pool.poll();
    }
    
    /**
     * @param uri
     * @param client
     */
    public void add(final URI uri, final Client client){
    	Objects.requireNonNull(uri);
    	Objects.requireNonNull(client);
        clients.computeIfPresent(
                uri.toString(), 
                (key, value) -> {
                    value.add(client);
                    return value;
                }
        );
    }
}
