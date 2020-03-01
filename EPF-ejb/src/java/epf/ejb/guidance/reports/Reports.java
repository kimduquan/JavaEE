/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.guidance.reports;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Reports {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Report> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<Report> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<Report> Contents) {
        this.Contents = Contents;
    }

}
