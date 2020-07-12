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
import openup.service.tasks.Task;
import openup.service.work_products.Artifact;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Role",
        title = "Role"
)
@Entity
@Table(name = "_Role")
public class Role implements Serializable {
    
    @Column
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "Role_Additionally_Performs",
            joinColumns = @JoinColumn(
                    name = "Role"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Task"
            )
    )
    private List<Task> additionallyPerforms;
    
    @ManyToMany
    @JoinTable(
            name = "Role_Modifies",
            joinColumns = @JoinColumn(
                    name = "Role"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Artifact"
            )
    )
    private List<Artifact> modifies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Name("Additionally_Performs")
    public List<Task> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(List<Task> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }
    
    @Name("Modifies")
    public List<Artifact> getModifies(){
        return modifies;
    }

    public void setModifies(List<Artifact> modifies) {
        this.modifies = modifies;
    }
}
