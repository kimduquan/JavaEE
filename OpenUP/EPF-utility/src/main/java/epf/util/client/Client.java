/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.client;

import java.net.URI;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author FOXCONN
 */
public class Client implements AutoCloseable {
    
    private URI uri;
    private javax.ws.rs.client.Client client;
    private ClientQueue clients;
    
    public Client(ClientQueue clients, URI uri){
        client = clients.poll(uri);
        this.uri = uri;
        this.clients = clients;
    }

    @Override
    public void close() throws Exception {
        clients.add(uri, client);
        uri = null;
        client = null;
    }
    
    public WebTarget target(){
        return client.target(uri);
    }
}
