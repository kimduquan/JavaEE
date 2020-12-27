/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client;

import java.net.URI;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author FOXCONN
 */
public class Client implements AutoCloseable {
    
    private Session session;
    private URI uri;
    private javax.ws.rs.client.Client client;
    
    public Client(Session session, URI uri){
        client = session.clients().poll(uri);
    }

    @Override
    public void close() throws Exception {
        session.clients().add(uri, client);
    }
    
    public WebTarget target(){
        return client.target(uri).register(session.header());
    }
}
