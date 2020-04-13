/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.event;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 *
 * @author FOXCONN
 */
public class MessageEvent {
    
    private String text;
    private String cid;
    private String destination;
    private String replyTo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }
    
    public MessageEvent copy(){
        MessageEvent clone = new MessageEvent();
        clone.cid = cid;
        clone.destination = destination;
        clone.replyTo = replyTo;
        clone.text = text;
        return clone;
    }
    
    public MessageEvent(TextMessage msg) throws JMSException{
        
        if(msg.propertyExists("cid")){
            this.cid = msg.getStringProperty("cid");
        }
        if(msg.propertyExists("destination")){
            this.destination = msg.getStringProperty("destination");
        }
        if(msg.propertyExists("replyTo")){
            this.replyTo = msg.getStringProperty("replyTo");
        }
        this.text = msg.getText();
    }
    
    public MessageEvent(){
        
    }
}
