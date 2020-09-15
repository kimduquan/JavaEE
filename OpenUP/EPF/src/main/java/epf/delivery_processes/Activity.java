/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.delivery_processes;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import epf.tasks.Task;
import javax.persistence.Column;
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
    
    @JoinColumn(name = "PARENT_ACTIVITIES", referencedColumnName = "NAME")
    private CapabilityPattern parentActivities;
    
    @ManyToMany
    @JoinTable(name = "ACTIVITY_TASKS",
            joinColumns = {@JoinColumn(name = "ACTIVITY")},
            inverseJoinColumns = {@JoinColumn(name = "TASK")}
    )
    private List<Task> tasks;

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
