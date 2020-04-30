/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import message.DestinationDefinition;

/**
 *
 * @author FOXCONN
 */
@Singleton
@LocalBean
@AccessTimeout(value=6, unit=TimeUnit.MINUTES)
public class ApplicationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private TemporaryQueue destinations;
    
    @Resource
    private ConnectionFactory factory;
    
    private JMSContext jms;
    
    @PostConstruct
    void postConstruct(){
        jms = factory.createContext();
        destinations = jms.createTemporaryQueue();
    }
    
    @PreDestroy
    void preDestroy(){
        try {
            destinations.delete();
            destinations = null;
            jms.close();
            jms = null;
        } catch (JMSException ex) {
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Lock(LockType.READ)
    public Queue getDestinations(){
        return destinations;
    }
    
    @Lock(LockType.WRITE)
    public Destination createDestination(Map<String, String> activationConfig, DestinationDefinition destDef) throws JMSException{
        Destination destination = null;
        if("javax.jms.Queue".equals(destDef.interfaceName)){
            destination = jms.createTemporaryQueue();
        }
        else if("javax.jms.Topic".equals(destDef.interfaceName)){
            destination = jms.createTemporaryTopic();
        }
        ObjectMessage objMsg = jms.createObjectMessage(destDef);
        activationConfig.forEach((key, value) -> {
            try {
                objMsg.setStringProperty(key, value);
            } catch (JMSException ex) {
                Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        objMsg.setJMSReplyTo(destination);
        jms.createProducer().send(destinations, objMsg);
        return destination;
    }
}
