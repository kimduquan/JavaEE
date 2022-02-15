package epf.delivery_processes.schema;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.MapsId;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(DeliveryProcesses.ACTIVITY)
@Schema(name = DeliveryProcesses.ACTIVITY, title = "Activity")
@Entity(name = DeliveryProcesses.ACTIVITY)
@Table(schema = DeliveryProcesses.SCHEMA, name = "ACTIVITY")
@NamedEntityGraph(includeAllAttributes = true)
public class Activity implements Serializable {
    
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
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private CapabilityPattern parentActivities;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "ACTIVITY_TASKS",
    		schema = DeliveryProcesses.SCHEMA,
    		joinColumns = @JoinColumn(name = "ACTIVITY"),
    		indexes = {@Index(columnList = "ACTIVITY")})
    @Column(name = "TASK")
    private List<String> tasks;
    
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

    public CapabilityPattern getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final CapabilityPattern parentActivities) {
        this.parentActivities = parentActivities;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(final List<String> tasks) {
        this.tasks = tasks;
    }
}
