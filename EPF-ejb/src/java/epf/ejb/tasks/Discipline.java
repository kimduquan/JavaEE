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
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Discipline {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Task> Tasks;

    /**
     * Get the value of Tasks
     *
     * @return the value of Tasks
     */
    public List<Task> getTasks() {
        return Tasks;
    }

    /**
     * Set the value of Tasks
     *
     * @param Tasks new value of Tasks
     */
    public void setTasks(List<Task> Tasks) {
        this.Tasks = Tasks;
    }

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
