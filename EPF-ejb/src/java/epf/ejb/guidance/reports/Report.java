/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.guidance.reports;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Report {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String MainDescription;

    /**
     * Get the value of MainDescription
     *
     * @return the value of MainDescription
     */
    public String getMainDescription() {
        return MainDescription;
    }

    /**
     * Set the value of MainDescription
     *
     * @param MainDescription new value of MainDescription
     */
    public void setMainDescription(String MainDescription) {
        this.MainDescription = MainDescription;
    }

}
