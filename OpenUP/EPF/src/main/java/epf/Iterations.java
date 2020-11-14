/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.delivery_processes.Iteration;
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
@Type
@Schema(title = "Iterations")
@Entity
@Table(name = "ITERATIONS")
public class Iterations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ITERATION")
    private Iteration iteration;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private Phases parentActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Iteration getIteration() {
        return iteration;
    }

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
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

    public Phases getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(Phases parentActivities) {
        this.parentActivities = parentActivities;
    }
}
