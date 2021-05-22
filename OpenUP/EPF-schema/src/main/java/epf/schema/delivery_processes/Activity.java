/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.EPF;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Type(EPF.ACTIVITY)
@Schema(name = EPF.ACTIVITY, title = "Activity")
@Entity(name = EPF.ACTIVITY)
@Table(schema = EPF.SCHEMA, name = "ACTIVITY")
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
    @ManyToMany
    @JoinTable(name = "ACTIVITY_TASKS",
            schema = EPF.SCHEMA,
            joinColumns = {@JoinColumn(name = "ACTIVITY")},
            inverseJoinColumns = {@JoinColumn(name = "TASK")},
            indexes = {@Index(columnList = "ACTIVITY")}
    )
    private List<Task> tasks;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }
}
