/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.roles;

import epf.ejb.tasks.Task;
import epf.ejb.work_products.Artifact;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Role {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Task> AdditionallyPerforms;

    /**
     * Get the value of AdditionallyPerforms
     *
     * @return the value of AdditionallyPerforms
     */
    public List<Task> getAdditionallyPerforms() {
        return AdditionallyPerforms;
    }

    /**
     * Set the value of AdditionallyPerforms
     *
     * @param AdditionallyPerforms new value of AdditionallyPerforms
     */
    public void setAdditionallyPerforms(List<Task> AdditionallyPerforms) {
        this.AdditionallyPerforms = AdditionallyPerforms;
    }

    private List<Artifact> Modifies;

    /**
     * Get the value of Modifies
     *
     * @return the value of Modifies
     */
    public List<Artifact> getModifies() {
        return Modifies;
    }

    /**
     * Set the value of Modifies
     *
     * @param Modifies new value of Modifies
     */
    public void setModifies(List<Artifact> Modifies) {
        this.Modifies = Modifies;
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
