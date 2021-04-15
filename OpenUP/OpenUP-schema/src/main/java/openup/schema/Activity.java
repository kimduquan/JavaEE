/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

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

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.ACTIVITY)
@Schema(name = OpenUP.ACTIVITY, title = "Activity")
@Entity(name = OpenUP.ACTIVITY)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_ACTIVITY", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Activity {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "ACTIVITY")
    private epf.schema.delivery_processes.Activity activity;
    
    /**
     * 
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private Iteration parentActivities;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(final Long activityId) {
        this.activityId = activityId;
    }

    public epf.schema.delivery_processes.Activity getActivity() {
        return activity;
    }

    public void setActivity(final epf.schema.delivery_processes.Activity activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public Iteration getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final Iteration parentActivities) {
        this.parentActivities = parentActivities;
    }
}
