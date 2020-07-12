/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.tasks;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import openup.service.roles.Role;
import openup.service.work_products.Artifact;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Task",
        title = "Task"
)
@Entity
@Table(name = "Task")
public class Task implements Serializable {
    
    @Column
    @Id
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "Task_Primary_Performer")
    private Role primaryPerformer;
    
    @ManyToMany
    @JoinTable(
            name = "Task_Inputs",
            joinColumns = @JoinColumn(
                    name = "Task"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Mandatory"
            )
    )
    private List<Artifact> mandatory;
    
    @ManyToMany
    @JoinTable(
            name = "Task_Inputs",
            joinColumns = @JoinColumn(
                    name = "Task"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Optional"
            )
    )
    private List<Artifact> optional;
    
    @ManyToMany
    @JoinTable(
            name = "Task_Outputs",
            joinColumns = @JoinColumn(
                    name = "Task"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Artifact"
            )
    )
    private List<Artifact> outputs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getPrimaryPerformer() {
        return primaryPerformer;
    }

    public void setPrimaryPerformer(Role primaryPerformer) {
        this.primaryPerformer = primaryPerformer;
    }

    public List<Artifact> getMandatory() {
        return mandatory;
    }

    public void setMandatory(List<Artifact> mandatory) {
        this.mandatory = mandatory;
    }

    public List<Artifact> getOptional() {
        return optional;
    }

    public void setOptional(List<Artifact> optional) {
        this.optional = optional;
    }

    public List<Artifact> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Artifact> outputs) {
        this.outputs = outputs;
    }
}
