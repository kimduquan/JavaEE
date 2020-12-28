/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.schema.roles.Role;
import epf.schema.roles.RoleSet;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("roles")
public class Roles implements Serializable {
    
    private List<RoleSet> roleSets;
    private List<Role> roles;

    public List<RoleSet> getRoleSets() {
        return roleSets;
    }

    public void setRoleSets(List<RoleSet> roleSets) {
        this.roleSets = roleSets;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
