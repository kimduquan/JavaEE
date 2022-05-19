package epf.delivery_processes.schema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.delivery_processes.schema.section.Properties;

/**
 *
 * @author FOXCONN
 */
@Type(DeliveryProcesses.MILESTONE)
@Schema(name = DeliveryProcesses.MILESTONE, title = "Milestone")
@Entity(name = DeliveryProcesses.MILESTONE)
@Table(schema = DeliveryProcesses.SCHEMA, name = "MILESTONE")
public class Milestone implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
	
    /**
     * 
     */
    @JoinColumn(name = "PREDECESSOR")
    private Iteration predecessor;
	
    /**
     * 
     */
    @Embedded
    @NotNull
    private Properties properties;
    
    /**
     * 
     */
    @Column(name = "REQUIRED_RESULTS")
    private Boolean requiredResults;
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(final Iteration predecessor) {
        this.predecessor = predecessor;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public Boolean getRequiredResults() {
        return requiredResults;
    }

    public void setRequiredResults(final Boolean requiredResults) {
        this.requiredResults = requiredResults;
    }
}
