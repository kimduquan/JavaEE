/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.tasks;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * List of tasks organized by discipline.
 */
@Stateful
@LocalBean
public class Tasks {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Discipline> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<Discipline> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<Discipline> Contents) {
        this.Contents = Contents;
    }

}
