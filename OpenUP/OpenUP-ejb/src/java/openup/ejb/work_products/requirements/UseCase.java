/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_products.requirements;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import openup.ejb.work_product_slot.TechnicalSpecification;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
@RolesAllowed("Analyst")
public class UseCase {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private List<TechnicalSpecification> FulfilledSlots;

    /**
     * Get the value of FulfilledSlots
     *
     * @return the value of FulfilledSlots
     */
    public List<TechnicalSpecification> getFulfilledSlots() {
        return FulfilledSlots;
    }

    /**
     * Set the value of FulfilledSlots
     *
     * @param FulfilledSlots new value of FulfilledSlots
     */
    public void setFulfilledSlots(List<TechnicalSpecification> FulfilledSlots) {
        this.FulfilledSlots = FulfilledSlots;
    }

}
