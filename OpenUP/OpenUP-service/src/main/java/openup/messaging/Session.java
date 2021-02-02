/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.messaging;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    
    @Inject
    @JMSConnectionFactory("jms/batch/connectionFactory")
    private JMSContext context;
    
    @Inject
    private Application application;
}
