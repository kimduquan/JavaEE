/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.openup;

import epf.schema.OpenUP;
import epf.schema.roles.RoleSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.Role)
@Schema(name = OpenUP.Role, title = "Role")
@Entity(name = OpenUP.Role)
@Table(schema = OpenUP.Schema, name = "OPENUP_ROLE")
public class Role {
    
    public static final String ANY_ROLE = "ANY_ROLE";

    @Id
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "ROLE")
    private epf.schema.roles.Role role;
    
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

    public epf.schema.roles.Role getRole() {
        return role;
    }

    public void setRole(epf.schema.roles.Role role) {
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
