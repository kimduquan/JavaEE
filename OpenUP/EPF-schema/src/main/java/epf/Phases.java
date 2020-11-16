/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.delivery_processes.Phase;
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
@Schema(title = "Phases")
@Entity
@Table(name = "_PHASES_", schema = "EPF")
public class Phases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "PHASE")
    private Phase phase;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private DeliveryProcesses parentActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryProcesses getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(DeliveryProcesses parentActivities) {
        this.parentActivities = parentActivities;
    }
}
