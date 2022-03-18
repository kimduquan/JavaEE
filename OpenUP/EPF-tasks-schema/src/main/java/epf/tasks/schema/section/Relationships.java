package epf.tasks.schema.section;

import epf.tasks.schema.Tasks;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.Index;
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
    @Column(name = "PRIMARY_PERFORMER", nullable = false)
    private String primaryPerformer;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "ROLE_ADDITIONALLY_PERFORMS",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ROLE", nullable = false)
    private Set<String> additionallyPerforms;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "TASK_INPUTS_MANDATORY",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT", nullable = false)
    private Set<String> mandatory;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "TASK_INPUTS_OPTIONAL",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT", nullable = false)
    private Set<String> optional;
    
    /**
     * 
     */
    
    @ElementCollection
    @CollectionTable(
    		name = "TASK_OUTPUTS",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT", nullable = false)
    private Set<String> outputs;

    public String getPrimaryPerformer() {
        return primaryPerformer;
    }

    public void setPrimaryPerformer(final String primaryPerformer) {
        this.primaryPerformer = primaryPerformer;
    }
    
    @Name("Additionally_Performs")
    public Set<String> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(final Set<String> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }

    public Set<String> getMandatory() {
        return mandatory;
    }

    public void setMandatory(final Set<String> mandatory) {
        this.mandatory = mandatory;
    }

    public Set<String> getOptional() {
        return optional;
    }

    public void setOptional(final Set<String> optional) {
        this.optional = optional;
    }

    public Set<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(final Set<String> outputs) {
        this.outputs = outputs;
    }
}
