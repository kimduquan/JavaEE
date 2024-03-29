package openup.schema.delivery_processes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import openup.schema.OpenUP;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.PHASE)
@Schema(name = OpenUP.PHASE, title = "Phase")
@Entity(name = OpenUP.PHASE)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_PHASE", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Phase implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PHASE")
    private epf.delivery_processes.schema.Phase phase;
    
    /**
     * 
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private DeliveryProcess parentActivities;
    
    @Override
    public String toString() {
    	return String.format("%s@%s:%d", getClass().getName(), name, id);
    }

    public epf.delivery_processes.schema.Phase getPhase() {
        return phase;
    }

    public void setPhase(final epf.delivery_processes.schema.Phase phase) {
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DeliveryProcess getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final DeliveryProcess parentActivities) {
        this.parentActivities = parentActivities;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
