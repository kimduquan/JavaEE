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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.MILESTONE)
@Schema(name = OpenUP.MILESTONE, title = "Milestone")
@Entity(name = OpenUP.MILESTONE)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_MILESTONE")
public class Milestone {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "MILESTONE")
    private epf.schema.delivery_processes.Milestone milestone;
    
    /**
     * 
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PREDECESSOR")
    private Iteration predecessor;

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(final Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public epf.schema.delivery_processes.Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(final epf.schema.delivery_processes.Milestone milestone) {
        this.milestone = milestone;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(final Iteration predecessor) {
        this.predecessor = predecessor;
    }
}
