/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.delivery_processes.section.WorkProductUsage;
import epf.schema.delivery_processes.section.TeamAllocation;
import epf.schema.delivery_processes.section.WorkBreakdownStructure;
import epf.schema.delivery_processes.section.Description;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Phase)
@Schema(name = EPF.Phase, title = "Phase")
@Entity(name = EPF.Phase)
@Table(schema = EPF.Schema, name = "PHASE", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
@NamedQuery(
        name = Phase.PHASES,
        query = "SELECT ph FROM EPF_Phase ph JOIN ph.parentActivities dp WHERE dp.name = :name"
)
public class Phase {
    
    public static final String PHASES = "EPF_Phase.Phases";

    @Column(name = "NAME")
    @Id
    private String name;
	
    @Embedded
    private Description description;

    @Embedded
    private WorkBreakdownStructure workBreakdownStructure;

    @Embedded
    private TeamAllocation teamAllocation;

    @Embedded
    private WorkProductUsage workProductUsage;
    
    @JoinColumn(name = "PARENT_ACTIVITIES")
    @ManyToOne
    private DeliveryProcess parentActivities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public WorkBreakdownStructure getWorkBreakdownStructure() {
        return workBreakdownStructure;
    }

    public void setWorkBreakdownStructure(WorkBreakdownStructure workBreakdownStructure) {
        this.workBreakdownStructure = workBreakdownStructure;
    }

    public TeamAllocation getTeamAllocation() {
        return teamAllocation;
    }

    public void setTeamAllocation(TeamAllocation teamAllocation) {
        this.teamAllocation = teamAllocation;
    }

    public WorkProductUsage getWorkProductUsage() {
        return workProductUsage;
    }

    public void setWorkProductUsage(WorkProductUsage workProductUsage) {
        this.workProductUsage = workProductUsage;
    }

    public DeliveryProcess getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(DeliveryProcess parentActivities) {
        this.parentActivities = parentActivities;
    }
}
