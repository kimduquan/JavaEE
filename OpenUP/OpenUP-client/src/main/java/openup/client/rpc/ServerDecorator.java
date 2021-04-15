/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.rpc;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 *
 * @author FOXCONN
 */
@Decorator
public class ServerDecorator implements Server {
    
    @Inject 
    @Delegate
    private Server server;
    
    @Override
    public Response<?> request(Request<?> request) throws Exception {
        return server.request(request);
    }
    
    @Override
    public void notify(Notification notification) {
        server.notify(notification);
    }
}
