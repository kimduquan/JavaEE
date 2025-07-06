package epf.delivery_processes.schema;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.delivery_processes.schema.section.Properties;

@Type(DeliveryProcesses.MILESTONE)
@Schema(name = DeliveryProcesses.MILESTONE, title = "Milestone")
@Entity(name = DeliveryProcesses.MILESTONE)
@Table(schema = DeliveryProcesses.SCHEMA, name = "MILESTONE")
public class Milestone implements Serializable {

    private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
	
    @JoinColumn(name = "PREDECESSOR")
    private Iteration predecessor;
	
    @Embedded
    @NotNull
    private Properties properties;
    
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
