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
@Type(OpenUP.ITERATION)
@Schema(name = OpenUP.ITERATION, title = "Iteration")
@Entity(name = OpenUP.ITERATION)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_ITERATION", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Iteration {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iterationId;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "ITERATION")
    private epf.schema.delivery_processes.Iteration iteration;
    
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
    private Phase parentActivities;

    public Long getIterationId() {
        return iterationId;
    }

    public void setIterationId(final Long iterationId) {
        this.iterationId = iterationId;
    }

    public epf.schema.delivery_processes.Iteration getIteration() {
        return iteration;
    }

    public void setIteration(final epf.schema.delivery_processes.Iteration iteration) {
        this.iteration = iteration;
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

    public Phase getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final Phase parentActivities) {
        this.parentActivities = parentActivities;
    }
}
