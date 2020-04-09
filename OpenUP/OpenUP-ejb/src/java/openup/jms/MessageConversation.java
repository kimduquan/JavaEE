/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.jms.Destination;

/**
 *
 * @author FOXCONN
 */
@ConversationScoped
public class MessageConversation implements Serializable {
    
    @Inject
    private Conversation conversation;
    
    private Destination destination;
    private String principalName;
    
    void begin(){
        if(conversation.isTransient()){
            conversation.begin();
        }
    }
    
    void end(){
        if(!conversation.isTransient()){
            conversation.begin();
        }
    }
    
    String getId(){
        return conversation.getId();
    }
    
    void setPrincipalName(String name){
        this.principalName = name;
    }
    
    void setDestination(Destination dest){
        this.destination = dest;
    }
    
    Destination getDestination(){
        return this.destination;
    }
    
    String getPrincipalName(){
        return this.principalName;
    }
}
