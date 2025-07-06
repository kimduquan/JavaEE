package epf;

import epf.roles.schema.Role;
import epf.roles.schema.RoleSet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Roles")
@Entity
@Table(name = "ROLES", schema = "EPF")
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
