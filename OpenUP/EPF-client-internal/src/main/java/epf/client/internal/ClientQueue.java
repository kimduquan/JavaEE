package epf.client.internal;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

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
     * @param uri
     * @param buildClient
     * @return
     */
    public Client poll(final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient){
    	Objects.requireNonNull(uri);
    	final Queue<Client> pool = clients.computeIfAbsent(
                uri.toString(), 
                key -> {
                    final Queue<Client> queue = new ConcurrentLinkedQueue<>();
                    if(buildClient == null) {
                        queue.add(ClientBuilder.newBuilder().build());
                    }
                    else {
                    	queue.add(buildClient.apply(ClientBuilder.newBuilder()).build());
                    }
                    return queue;
                }
        );
        if(pool.isEmpty()){
        	if(buildClient == null) {
        		pool.add(ClientBuilder.newBuilder().build());
        	}
        	else {
                pool.add(buildClient.apply(ClientBuilder.newBuilder()).build());
        	}
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
