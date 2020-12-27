/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.delivery_processes.Milestone;
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
@Schema(title = "_Milestones_")
@Entity
@Table(name = "_MILESTONES_", schema = "EPF")
public class Milestones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MILESTONE")
    private Milestone milestone;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PREDECESSOR")
    private Iterations predecessor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterations getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Iterations predecessor) {
        this.predecessor = predecessor;
    }
}
