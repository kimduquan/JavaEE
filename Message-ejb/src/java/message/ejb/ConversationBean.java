/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.jms.Destination;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class ConversationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Destination destination;
    private String principalName;
}
