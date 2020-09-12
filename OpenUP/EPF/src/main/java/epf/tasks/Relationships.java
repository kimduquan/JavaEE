/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tasks;

import epf.roles.Role;
import epf.work_products.Artifact;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Relationships {
    
    @ManyToOne
    @JoinColumn(name = "TASK_PRIMARY_PERFORMER")
    private Role primaryPerformer;
    
    @ManyToMany
    @JoinTable(
            name = "TASK_INPUTS_MANDATORY",
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> mandatory;
    
    @ManyToMany
    @JoinTable(
            name = "TASK_INPUTS_OPTIONAL",
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> optional;
    
    @ManyToMany
    @JoinTable(
            name = "TASK_OUTPUTS",
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> outputs;

    @JsonbTransient
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
