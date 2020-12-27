/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultTrustManager;

/**
 *
 * @author FOXCONN
 */
@Dependent
public class ClientQueue implements Serializable {
    
    private Map<String, Queue<Client>> clients;
    private SSLContext context;
    
    @PostConstruct
    void postConstruct(){
        clients = new ConcurrentHashMap<>();
        context = buildContext();
    }
    
    @PreDestroy
    void preDestroy(){
        clients.forEach((name, queue) -> {
            queue.forEach(Client::close);
            queue.clear();
        });
        clients.clear();
    }
    
    static SSLContext buildContext(){
        try {
            TrustManager x509 = new DefaultTrustManager();
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509}, null);
            return ctx;
        } 
        catch (Exception ex) {
            Logger.getLogger(ClientQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
