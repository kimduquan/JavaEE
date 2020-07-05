/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.roles;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Role Set",
        title = "Role Set"
)
@Entity
@Table(name = "RoleSet")
public class RoleSet implements Serializable {
    
    @Column
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "Roles",
            joinColumns = @JoinColumn(
                    name = "RoleSet"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Role"
            )
    )
    private List<Role> roles;
    
    @Name("Roles")
    public List<Role> getRoles(){
        return roles;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
