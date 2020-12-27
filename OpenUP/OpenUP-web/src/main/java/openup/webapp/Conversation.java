/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author FOXCONN
 */
@ConversationScoped
@Named("webapp_conversation")
public class Conversation implements Serializable {
    
    @Inject
    private javax.enterprise.context.Conversation conversation;
    
    @PostConstruct
    void postConstruct(){
        if(conversation.isTransient()){
            conversation.begin();
        }
    }
    
    public void end(){
        if(!conversation.isTransient()){
            conversation.end();
        }
    }
    
    public String getId(){
        return conversation.getId();
    }
}
