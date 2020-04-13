/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.context;

import message.ejb.ConversationBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

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
    
    public String begin(){
        String cid = "";
        if(scope.isTransient()){
            scope.begin();
            cid = scope.getId();
        }
        return cid;
    }
    
    public void end(){
        if(!scope.isTransient()){
            scope.end();
        }
    }
    
    public ConversationBean getConversation(){
        return conversation;
    }
}
