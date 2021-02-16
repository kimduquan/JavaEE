/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.net.URI;
import java.util.Map;
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
import epf.util.ssl.DefaultSSLContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class ClientQueue {
	
	private Map<String, Queue<Client>> clients;
    private SSLContext context;
    
    @PostConstruct
    public void initialize(){
        clients = new ConcurrentHashMap<>();
        context = DefaultSSLContext.build();
    }
    
    @PreDestroy
    public void close(){
        clients.forEach((name, queue) -> {
            queue.forEach(Client::close);
            queue.clear();
        });
        clients.clear();
    }
    
    static ClientBuilder newBuilder(SSLContext context){
    	return ClientBuilder
    			.newBuilder()
    			.sslContext(context)
                .hostnameVerifier(new DefaultHostnameVerifier());
    }
    
    public Client poll(URI uri, Function<ClientBuilder, ClientBuilder> buildClient){
        Queue<Client> pool = clients.computeIfAbsent(
                uri.toString(), 
                key -> {
                    Queue<Client> queue = new ConcurrentLinkedQueue<>();
                    Client client = buildClient.apply(newBuilder(context)).build();
                    queue.add(client);
                    return queue;
                }
        );
        if(pool.isEmpty()){
        	Client client = buildClient.apply(newBuilder(context)).build();
            pool.add(client);
        }
        return pool.poll();
    }
    
    public void add(URI uri, Client client){
        clients.computeIfPresent(
                uri.toString(), 
                (key, value) -> {
                    value.add(client);
                    return value;
                }
        );
    }
}
