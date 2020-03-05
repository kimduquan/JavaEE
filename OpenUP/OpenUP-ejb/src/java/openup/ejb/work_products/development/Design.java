/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_products.development;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import openup.ejb.work_product_slot.TechnicalDesign;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
@RolesAllowed("Developer")
public class Design {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
        private List<TechnicalDesign> FulfilledSlots;

    /**
     * Get the value of FulfilledSlots
     *
     * @return the value of FulfilledSlots
     */
    public List<TechnicalDesign> getFulfilledSlots() {
        return FulfilledSlots;
    }

    /**
     * Set the value of FulfilledSlots
     *
     * @param FulfilledSlots new value of FulfilledSlots
     */
    public void setFulfilledSlots(List<TechnicalDesign> FulfilledSlots) {
        this.FulfilledSlots = FulfilledSlots;
    }

}
