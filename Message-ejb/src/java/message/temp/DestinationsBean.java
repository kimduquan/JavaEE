/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;

/**
 *
 * @author FOXCONN
 */
@Singleton
@LocalBean
@AccessTimeout(value=6, unit=TimeUnit.MINUTES)
public class DestinationsBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private ConcurrentHashMap<String, TemporaryQueue> temporaryQueues = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, TemporaryTopic> temporaryTopics = new ConcurrentHashMap<>();
    
    @Resource
    private ConnectionFactory factory;
    
    private JMSContext jms;
    
    @PostConstruct
    void postConstruct(){
        jms = factory.createContext();
    }
    
    @PreDestroy
    void preDestroy(){
        temporaryQueues.clear();
        temporaryTopics.clear();
        jms.close();
        jms = null;
    }
    
    @Lock(LockType.WRITE)
    public TemporaryQueue createTemporaryQueue(String name){
        TemporaryQueue queue = jms.createTemporaryQueue();
        temporaryQueues.put(name, queue);
        return queue;
    }
    
    @Lock(LockType.WRITE)
    public TemporaryTopic createTemporaryTopic(String name){
        TemporaryTopic topic = jms.createTemporaryTopic();
        temporaryTopics.put(name, topic);
        return topic;
    }
}
