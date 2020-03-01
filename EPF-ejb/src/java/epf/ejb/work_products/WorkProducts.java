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
 * List of work products organized by domain.
 */
@Stateful
@LocalBean
public class WorkProducts {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Domain> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<Domain> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<Domain> Contents) {
        this.Contents = Contents;
    }

}
