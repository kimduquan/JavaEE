package epf.tasks.schema.section;

import epf.tasks.schema.Tasks;
import java.io.Serializable;
import java.util.List;
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
    @Column(name = "ROLE")
    private List<String> additionallyPerforms;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "TASK_INPUTS_MANDATORY",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT")
    private List<String> mandatory;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "TASK_INPUTS_OPTIONAL",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT")
    private List<String> optional;
    
    /**
     * 
     */
    
    @ElementCollection
    @CollectionTable(
    		name = "TASK_OUTPUTS",
    		schema = Tasks.SCHEMA,
    		joinColumns = @JoinColumn(name = "TASK"),
    		indexes = {@Index(columnList = "TASK")})
    @Column(name = "ARTIFACT")
    private List<String> outputs;

    public String getPrimaryPerformer() {
        return primaryPerformer;
    }

    public void setPrimaryPerformer(final String primaryPerformer) {
        this.primaryPerformer = primaryPerformer;
    }
    
    @Name("Additionally_Performs")
    public List<String> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(final List<String> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }

    public List<String> getMandatory() {
        return mandatory;
    }

    public void setMandatory(final List<String> mandatory) {
        this.mandatory = mandatory;
    }

    public List<String> getOptional() {
        return optional;
    }

    public void setOptional(final List<String> optional) {
        this.optional = optional;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(final List<String> outputs) {
        this.outputs = outputs;
    }
}
