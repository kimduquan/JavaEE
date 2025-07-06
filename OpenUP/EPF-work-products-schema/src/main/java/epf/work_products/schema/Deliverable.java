package epf.work_products.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;

@Type(WorkProducts.DELIVERABLE)
@Schema(name = WorkProducts.DELIVERABLE, title = "Deliverable")
@Entity(name = WorkProducts.DELIVERABLE)
@Table(schema = WorkProducts.SCHEMA, name = "DELIVERABLE")
public class Deliverable extends EPFEntity {
    
    private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
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
