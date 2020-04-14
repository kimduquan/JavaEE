/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp.context;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import message.temp.ConversationBean;

/**
 *
 * @author FOXCONN
 */
@ConversationScoped
public class TemporaryConversation implements Serializable {
    
    @Inject
    private Conversation scope;
    
    @EJB
    private ConversationBean conversaction;
    
    public void begin(){
        if(scope.isTransient()){
            scope.begin();
            conversaction.begin(scope.getId(), 6000);
        }
    }
    
    public void end(){
        if(!scope.isTransient()){
            scope.end();
            conversaction.end();
        }
    }
}
