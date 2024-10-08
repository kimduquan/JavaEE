package epf.delivery_processes.schema;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author FOXCONN
 */
@Type(DeliveryProcesses.ITERATION)
@Schema(name = DeliveryProcesses.ITERATION, title = "Iteration")
@Entity(name = DeliveryProcesses.ITERATION)
@Table(schema = DeliveryProcesses.SCHEMA, name = "ITERATION", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Iteration implements Serializable {
    
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
    private CapabilityPattern extend;
    
    /**
     * 
     */
    @Column(name = "NUMBER")
    private Integer number;
    
    /**
     * 
     */
    @JoinColumn(name = "PARENT_ACTIVITIES")
    @ManyToOne
    private Phase parentActivities;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(name = "ITERATION_ACTIVITIES",
            schema = DeliveryProcesses.SCHEMA,
            joinColumns = {@JoinColumn(name = "ITERATION")},
            inverseJoinColumns = {@JoinColumn(name = "ACTIVITY")},
            indexes = {@Index(columnList = "ITERATION")}
    )
    private Set<Activity> activities;
    
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

    public CapabilityPattern getExtend() {
        return extend;
    }

    public void setExtend(final CapabilityPattern extend) {
        this.extend = extend;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Phase getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final Phase parentActivities) {
        this.parentActivities = parentActivities;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(final Set<Activity> activities) {
        this.activities = activities;
    }
}
