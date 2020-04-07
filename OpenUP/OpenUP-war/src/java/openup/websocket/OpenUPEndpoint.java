/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.websocket;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author FOXCONN
 */
@ServerEndpoint("/message")
public class OpenUPEndpoint {

    @OnMessage
    public String onMessage(String message) {
        return message;
    }
    
}
