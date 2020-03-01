/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.roles;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * This category lists roles organized by role set.
 */
@Stateful
@LocalBean
public class Roles {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<RoleSet> Contents;

    /**
     * Get the value of Contents
     *
     * @return the value of Contents
     */
    public List<RoleSet> getContents() {
        return Contents;
    }

    /**
     * Set the value of Contents
     *
     * @param Contents new value of Contents
     */
    public void setContents(List<RoleSet> Contents) {
        this.Contents = Contents;
    }

}
