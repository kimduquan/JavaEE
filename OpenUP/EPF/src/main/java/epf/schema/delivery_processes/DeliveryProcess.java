/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.delivery_processes.section.WorkBreakdownStructure;
import epf.schema.delivery_processes.section.Description;
import epf.schema.delivery_processes.section.TeamAllocation;
import epf.schema.delivery_processes.section.WorkProductUsage;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "DeliveryProcess",
        title = "Delivery Process"
)
@Entity
@Table(name = "DELIVERY_PROCESS", schema = "EPF")
@NamedQuery(name = "DeliveryProcess.DeliveryProcesses", query = "SELECT dp FROM DeliveryProcess AS dp")
public class DeliveryProcess {
	
    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
	
    @Embedded
    private Description description;

    @Embedded
    private WorkBreakdownStructure workBreakdownStructure;

    @Embedded
    private TeamAllocation teamAllocation;

    @Embedded
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
