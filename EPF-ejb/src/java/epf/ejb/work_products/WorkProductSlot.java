/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.work_products;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class WorkProductSlot {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Artifact> FulfillingWorkProducts;

    /**
     * Get the value of FulfillingWorkProducts
     *
     * @return the value of FulfillingWorkProducts
     */
    public List<Artifact> getFulfillingWorkProducts() {
        return FulfillingWorkProducts;
    }

    /**
     * Set the value of FulfillingWorkProducts
     *
     * @param FulfillingWorkProducts new value of FulfillingWorkProducts
     */
    public void setFulfillingWorkProducts(List<Artifact> FulfillingWorkProducts) {
        this.FulfillingWorkProducts = FulfillingWorkProducts;
    }

}
