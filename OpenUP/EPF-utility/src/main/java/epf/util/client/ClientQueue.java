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
    void postConstruct(){
        clients = new ConcurrentHashMap<>();
        context = DefaultSSLContext.build();
    }
    
    @PreDestroy
    void preDestroy(){
        clients.forEach((name, queue) -> {
            queue.forEach(Client::close);
            queue.clear();
        });
        clients.clear();
    }
    
    static Client buildClient(SSLContext context){
        return ClientBuilder.newBuilder()
                .sslContext(context)
                .hostnameVerifier(new DefaultHostnameVerifier())
                .build();
    }
    
    public Client poll(URI uri){
        Queue<Client> pool = clients.computeIfAbsent(
                uri.toString(), 
                key -> {
                    Queue<Client> queue = new ConcurrentLinkedQueue<>();
                    Client client = buildClient(context);
                    queue.add(client);
                    return queue;
                }
        );
        if(pool.isEmpty()){
            Client client = buildClient(context);
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
