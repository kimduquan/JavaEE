/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.delivery_processes;

import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import openup.service.tasks.Task;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "NAME")
public class Activity extends CapabilityPattern {
    
    @OneToOne
    @JoinColumn(name = "PARENT_ACTIVITIES", referencedColumnName = "NAME")
    private CapabilityPattern parentActivities;
    
    @OneToMany
    @JoinTable(name = "ACTIVITY_TASKS",
            joinColumns = {@JoinColumn(name = "NAME")},
            inverseJoinColumns = {@JoinColumn(name = "NAME")}
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
