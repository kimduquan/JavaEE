/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.roles.Role;
import epf.schema.roles.RoleSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Schema(title = "_Roles_")
@Entity
@Table(name = "_ROLES_", schema = "EPF")
public class Roles {

    @Id
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "ROLE")
    private Role role;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToOne
    @JoinColumn(name = "ROLE_SETS")
    private RoleSet roleSets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public RoleSet getRoleSets() {
        return roleSets;
    }

    public void setRoleSets(RoleSet roleSets) {
        this.roleSets = roleSets;
    }
}
