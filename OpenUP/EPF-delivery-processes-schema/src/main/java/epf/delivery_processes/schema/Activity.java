package epf.delivery_processes.schema;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;

@Type(DeliveryProcesses.ACTIVITY)
@Schema(name = DeliveryProcesses.ACTIVITY, title = "Activity")
@Entity(name = DeliveryProcesses.ACTIVITY)
@Table(schema = DeliveryProcesses.SCHEMA, name = "ACTIVITY")
public class Activity extends EPFEntity {
    
    private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "NAME")
    @NotNull
    private CapabilityPattern extend;
    
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private CapabilityPattern parentActivities;
    
    @ElementCollection
    @CollectionTable(
    		name = "ACTIVITY_TASKS",
    		schema = DeliveryProcesses.SCHEMA,
    		joinColumns = @JoinColumn(name = "ACTIVITY"),
    		indexes = {@Index(columnList = "ACTIVITY")})
    @Column(name = "TASK")
    private Set<String> tasks;
    
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

    public Set<String> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<String> tasks) {
        this.tasks = tasks;
    }
}
