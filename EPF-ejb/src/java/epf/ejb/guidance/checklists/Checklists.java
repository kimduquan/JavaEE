/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.guidance.checklists;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * Alphabetical listing of all checklists.
 */
@Stateful
@LocalBean
public class Checklists {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Checklist> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<Checklist> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<Checklist> Contents) {
        this.Contents = Contents;
    }

    private List CheckItems;

    /**
     * Get the value of CheckItems
     *
     * @return the value of CheckItems
     */
    public List getCheckItems() {
        return CheckItems;
    }

    /**
     * Set the value of CheckItems
     *
     * @param CheckItems new value of CheckItems
     */
    public void setCheckItems(List CheckItems) {
        this.CheckItems = CheckItems;
    }

}
