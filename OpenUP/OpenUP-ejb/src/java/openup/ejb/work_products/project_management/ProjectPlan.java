/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_products.project_management;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import openup.ejb.work_product_slot.ProjectDefinitionAndScope;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class ProjectPlan {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
        private List<ProjectDefinitionAndScope> FulfilledSlots;

    /**
     * Get the value of FulfilledSlots
     *
     * @return the value of FulfilledSlots
     */
    public List<ProjectDefinitionAndScope> getFulfilledSlots() {
        return FulfilledSlots;
    }

    /**
     * Set the value of FulfilledSlots
     *
     * @param FulfilledSlots new value of FulfilledSlots
     */
    public void setFulfilledSlots(List<ProjectDefinitionAndScope> FulfilledSlots) {
        this.FulfilledSlots = FulfilledSlots;
    }

}
