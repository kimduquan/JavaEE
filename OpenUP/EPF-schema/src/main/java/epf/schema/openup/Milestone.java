/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.openup;

import epf.schema.OpenUP;
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
@Type(OpenUP.Milestone)
@Schema(name = OpenUP.Milestone, title = "Milestone")
@Entity(name = OpenUP.Milestone)
@Table(schema = OpenUP.Schema, name = "MILESTONE")
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MILESTONE")
    private epf.schema.delivery_processes.Milestone milestone;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PREDECESSOR")
    private Iteration predecessor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public epf.schema.delivery_processes.Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(epf.schema.delivery_processes.Milestone milestone) {
        this.milestone = milestone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Iteration predecessor) {
        this.predecessor = predecessor;
    }
}
