/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import message.DestinationDefinition;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.event.Observes;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
public class DestinationObserverBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void observe(@Observes DestinationDefinition destDef){
        
    }
}
