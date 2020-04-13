/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
public class TopicListenerBean extends TemporaryListener {

    @Override
    protected void onRealMessage(Message tempMsg, Message realMsg) throws JMSException {
        
    }
    
}
