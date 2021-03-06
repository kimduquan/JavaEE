/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.EPF;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import epf.schema.tasks.Task;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Activity)
@Schema(name = EPF.Activity, title = "Activity")
@Entity(name = EPF.Activity)
@Table(schema = EPF.Schema, name = "ACTIVITY")
public class Activity {
    
    @Column(name = "NAME")
    @Id
    private String name;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "NAME")
    private CapabilityPattern Extends;
    
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private CapabilityPattern parentActivities;
    
    @ManyToMany
    @JoinTable(name = "ACTIVITY_TASKS",
            schema = EPF.Schema,
            joinColumns = {@JoinColumn(name = "ACTIVITY")},
            inverseJoinColumns = {@JoinColumn(name = "TASK")},
            indexes = {@Index(columnList = "ACTIVITY")}
    )
    private List<Task> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CapabilityPattern getExtends() {
        return Extends;
    }

    public void setExtends(CapabilityPattern Extends) {
        this.Extends = Extends;
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
