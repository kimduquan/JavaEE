package epf.work_products.schema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(WorkProducts.DELIVERABLE)
@Schema(name = WorkProducts.DELIVERABLE, title = "Deliverable")
@Entity(name = WorkProducts.DELIVERABLE)
@Table(schema = WorkProducts.SCHEMA, name = "DELIVERABLE")
@NamedEntityGraph(includeAllAttributes = true)
public class Deliverable implements Serializable {
    
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
    @OneToOne
    @MapsId
    @JoinColumn(name = "NAME")
    @NotNull
    private Artifact artifact;
    
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

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(final Artifact artifact) {
        this.artifact = artifact;
    }
}
