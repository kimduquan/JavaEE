package epf.delivery_processes.schema;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.delivery_processes.schema.section.Description;
import epf.delivery_processes.schema.section.TeamAllocation;
import epf.delivery_processes.schema.section.WorkBreakdownStructure;
import epf.delivery_processes.schema.section.WorkProductUsage;

@Type(DeliveryProcesses.DELIVERY_PROCESS)
@Schema(name = DeliveryProcesses.DELIVERY_PROCESS, title = "Delivery Process")
@Entity(name = DeliveryProcesses.DELIVERY_PROCESS)
@Table(schema = DeliveryProcesses.SCHEMA, name = "DELIVERY_PROCESS")
public class DeliveryProcess implements Serializable {
	
    private static final long serialVersionUID = 1L;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
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
}
