/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.openup;

import epf.schema.delivery_processes.Activity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Schema(title = "_Activities_")
@Entity
@Table(name = "_ACTIVITIES_", schema = "EPF")
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ACTIVITY")
    private Activity activity;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private Iterations parentActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Iterations getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(Iterations parentActivities) {
        this.parentActivities = parentActivities;
    }
}
