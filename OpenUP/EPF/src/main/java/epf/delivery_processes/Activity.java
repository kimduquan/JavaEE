/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.delivery_processes;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import epf.tasks.Task;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Activity",
        title = "Activity"
)
@Entity
@Table(name = "ACTIVITY")
public class Activity extends CapabilityPattern {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @ManyToOne
    @JoinTable(name = "ACTIVITY_PARENT",
            joinColumns = {@JoinColumn(name = "ACTIVITY")},
            inverseJoinColumns = {@JoinColumn(name = "PARENT")}
    )
    private CapabilityPattern parentActivities;
    
    @ManyToMany
    @JoinTable(name = "ACTIVITY_TASKS",
            joinColumns = {@JoinColumn(name = "ACTIVITY")},
            inverseJoinColumns = {@JoinColumn(name = "TASK")}
    )
    private List<Task> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CapabilityPattern getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(CapabilityPattern parentActivities) {
        this.parentActivities = parentActivities;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
