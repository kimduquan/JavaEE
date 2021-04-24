/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles.section;

import epf.schema.EPF;
import epf.schema.tasks.Task;
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
    @ManyToMany
    @JoinTable(
            name = "ROLE_ADDITIONALLY_PERFORMS",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "TASK"
            )
    )
    private List<Task> additionallyPerforms;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "ROLE_MODIFIES",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> modifies;
    
    @Name("Additionally_Performs")
    public List<Task> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(final List<Task> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }
    
    @Name("Modifies")
    public List<Artifact> getModifies(){
        return modifies;
    }

    public void setModifies(final List<Artifact> modifies) {
        this.modifies = modifies;
    }
}
