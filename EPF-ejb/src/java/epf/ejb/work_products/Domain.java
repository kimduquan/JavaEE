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
public class Domain {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Artifact> WorkProducts;

    /**
     * Get the value of WorkProducts
     *
     * @return the value of WorkProducts
     */
    public List<Artifact> getWorkProducts() {
        return WorkProducts;
    }

    /**
     * Set the value of WorkProducts
     *
     * @param WorkProducts new value of WorkProducts
     */
    public void setWorkProducts(List<Artifact> WorkProducts) {
        this.WorkProducts = WorkProducts;
    }

}
