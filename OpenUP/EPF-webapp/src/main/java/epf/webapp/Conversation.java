/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

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
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
    private transient javax.enterprise.context.Conversation conversation;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct(){
        if(conversation.isTransient()){
            conversation.begin();
        }
    }
    
    /**
     * 
     */
    public void end(){
        if(!conversation.isTransient()){
            conversation.end();
        }
    }
    
    /**
     * @return
     */
    public String getId(){
        return conversation.getId();
    }
}
