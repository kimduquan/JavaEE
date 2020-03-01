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
 * 
 */
@Stateful
@LocalBean
public class RoleSet {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private List<Role> Roles;

    /**
     * Get the value of Roles
     *
     * @return the value of Roles
     */
    public List<Role> getRoles() {
        return Roles;
    }

    /**
     * Set the value of Roles
     *
     * @param Roles new value of Roles
     */
    public void setRoles(List<Role> Roles) {
        this.Roles = Roles;
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
