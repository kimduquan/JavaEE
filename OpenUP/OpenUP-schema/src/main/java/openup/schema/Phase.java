/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import openup.schema.DeliveryProcess;
import openup.schema.OpenUP;
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
@Type(OpenUP.Phase)
@Schema(name = OpenUP.Phase, title = "Phase")
@Entity(name = OpenUP.Phase)
@Table(schema = OpenUP.Schema, name = "OPENUP_PHASE", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "PHASE")
    private epf.schema.delivery_processes.Phase phase;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private DeliveryProcess parentActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public epf.schema.delivery_processes.Phase getPhase() {
        return phase;
    }

    public void setPhase(epf.schema.delivery_processes.Phase phase) {
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryProcess getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(DeliveryProcess parentActivities) {
        this.parentActivities = parentActivities;
    }
}
