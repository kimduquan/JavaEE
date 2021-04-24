/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import java.io.Serializable;
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
@Type(OpenUP.PHASE)
@Schema(name = OpenUP.PHASE, title = "Phase")
@Entity(name = OpenUP.PHASE)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_PHASE", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Phase implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phaseId;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PHASE")
    private epf.schema.delivery_processes.Phase phase;
    
    /**
     * 
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private DeliveryProcess parentActivities;

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(final Long phaseId) {
        this.phaseId = phaseId;
    }

    public epf.schema.delivery_processes.Phase getPhase() {
        return phase;
    }

    public void setPhase(final epf.schema.delivery_processes.Phase phase) {
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DeliveryProcess getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final DeliveryProcess parentActivities) {
        this.parentActivities = parentActivities;
    }
}
