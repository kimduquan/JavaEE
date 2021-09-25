/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.delivery_processes.schema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import epf.delivery_processes.schema.section.Description;
import epf.delivery_processes.schema.section.TeamAllocation;
import epf.delivery_processes.schema.section.WorkBreakdownStructure;
import epf.delivery_processes.schema.section.WorkProductUsage;
import epf.schema.EPF;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.PHASE)
@Schema(name = EPF.PHASE, title = "Phase")
@Entity(name = EPF.PHASE)
@Table(schema = EPF.SCHEMA, name = "PHASE", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
@NamedQuery(
        name = Phase.PHASES,
        query = "SELECT ph FROM EPF_Phase ph JOIN ph.parentActivities dp WHERE dp.name = :name"
)
public class Phase implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public static final String PHASES = "EPF_Phase.Phases";

    /**
     * 
     */
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
	
    /**
     * 
     */
    @Embedded
    @NotNull
    private Description description;

    /**
     * 
     */
    @Embedded
    @NotNull
    private WorkBreakdownStructure workBreakdownStructure;

    /**
     * 
     */
    @Embedded
    @NotNull
    private TeamAllocation teamAllocation;

    /**
     * 
     */
    @Embedded
    @NotNull
    private WorkProductUsage workProductUsage;
    
    /**
     * 
     */
    @JoinColumn(name = "PARENT_ACTIVITIES")
    @ManyToOne
    private DeliveryProcess parentActivities;
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(final Description description) {
        this.description = description;
    }

    public WorkBreakdownStructure getWorkBreakdownStructure() {
        return workBreakdownStructure;
    }

    public void setWorkBreakdownStructure(final WorkBreakdownStructure workBreakdownStructure) {
        this.workBreakdownStructure = workBreakdownStructure;
    }

    public TeamAllocation getTeamAllocation() {
        return teamAllocation;
    }

    public void setTeamAllocation(final TeamAllocation teamAllocation) {
        this.teamAllocation = teamAllocation;
    }

    public WorkProductUsage getWorkProductUsage() {
        return workProductUsage;
    }

    public void setWorkProductUsage(final WorkProductUsage workProductUsage) {
        this.workProductUsage = workProductUsage;
    }

    public DeliveryProcess getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final DeliveryProcess parentActivities) {
        this.parentActivities = parentActivities;
    }
}
