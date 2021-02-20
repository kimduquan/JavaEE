/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.delivery_processes.section.WorkProductUsage;
import epf.schema.delivery_processes.section.TeamAllocation;
import epf.schema.delivery_processes.section.Description;
import epf.schema.delivery_processes.section.WorkBreakdownStructure;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.CapabilityPattern)
@Schema(name = EPF.CapabilityPattern, title = "Capability Pattern")
@Entity(name = EPF.CapabilityPattern)
@Table(schema = EPF.Schema, name = "CAPABILITY_PATTERN")
public class CapabilityPattern {
	
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
	
    @Embedded
    @NotNull
    private Description description;

    @Embedded
    @NotNull
    private WorkBreakdownStructure workBreakdownStructure;

    @Embedded
    @NotNull
    private TeamAllocation teamAllocation;

    @Embedded
    @NotNull
    private WorkProductUsage workProductUsage;

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
}
