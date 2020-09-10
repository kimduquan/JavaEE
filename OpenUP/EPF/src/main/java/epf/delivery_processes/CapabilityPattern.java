/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.delivery_processes;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "CapabilityPattern",
        title = "Capability Pattern"
)
@Entity
@Table(name = "CAPABILITY_PATTERN")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "NAME", length = 63)
public class CapabilityPattern {
	
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
}
