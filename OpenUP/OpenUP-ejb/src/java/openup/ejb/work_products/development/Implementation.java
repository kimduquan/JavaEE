/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_products.development;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import openup.ejb.work_product_slot.TechnicalImplementation;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Implementation {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private List<TechnicalImplementation> FulfilledSlots;

    /**
     * Get the value of FulfilledSlots
     *
     * @return the value of FulfilledSlots
     */
    public List<TechnicalImplementation> getFulfilledSlots() {
        return FulfilledSlots;
    }

    /**
     * Set the value of FulfilledSlots
     *
     * @param FulfilledSlots new value of FulfilledSlots
     */
    public void setFulfilledSlots(List<TechnicalImplementation> FulfilledSlots) {
        this.FulfilledSlots = FulfilledSlots;
    }

}
