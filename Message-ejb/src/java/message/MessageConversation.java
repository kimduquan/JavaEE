/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import message.ejb.ConversationBean;
import java.io.Serializable;
import javax.ejb.EJB;
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
    private Conversation scope;
    
    @EJB
    private ConversationBean conversation;
    
    private Destination destination;
    private String principalName;
    
    public void begin(){
        if(scope.isTransient()){
            scope.begin();
        }
    }
    
    public void end(){
        if(!scope.isTransient()){
            scope.begin();
        }
    }
    
    public ConversationBean getConversation() {
        return conversation;
    }
    
    public String getId(){
        return scope.getId();
    }
    
    public void setPrincipalName(String name){
        this.principalName = name;
    }
    
    public void setDestination(Destination dest){
        this.destination = dest;
    }
    
    public Destination getDestination(){
        return this.destination;
    }
    
    public String getPrincipalName(){
        return this.principalName;
    }
}
