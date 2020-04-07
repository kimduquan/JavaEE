/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.MessageDriven;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author FOXCONN
 */
@MessageDriven
public class Listener implements MessageListener {

    @Inject
    @Push(channel="message")
    PushContext push;
    
    ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @Override
    public void onMessage(Message message) {
        
    }
    
}
