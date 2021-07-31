/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.tasks.section;

import epf.schema.EPF;
import epf.schema.roles.Role;
import epf.schema.work_products.Artifact;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Embeddable
public class Relationships implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @JoinColumn(name = "PRIMARY_PERFORMER", referencedColumnName = "NAME")
    private Role primaryPerformer;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "ROLE_ADDITIONALLY_PERFORMS",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE"
            )
    )
    private List<Role> additionallyPerforms;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "TASK_INPUTS_MANDATORY",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> mandatory;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "TASK_INPUTS_OPTIONAL",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> optional;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "TASK_OUTPUTS",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "TASK"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> outputs;

    public Role getPrimaryPerformer() {
        return primaryPerformer;
    }

    public void setPrimaryPerformer(final Role primaryPerformer) {
        this.primaryPerformer = primaryPerformer;
    }
    
    @Name("Additionally_Performs")
    public List<Role> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(final List<Role> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }

    public List<Artifact> getMandatory() {
        return mandatory;
    }

    public void setMandatory(final List<Artifact> mandatory) {
        this.mandatory = mandatory;
    }

    public List<Artifact> getOptional() {
        return optional;
    }

    public void setOptional(final List<Artifact> optional) {
        this.optional = optional;
    }

    public List<Artifact> getOutputs() {
        return outputs;
    }

    public void setOutputs(final List<Artifact> outputs) {
        this.outputs = outputs;
    }
}
