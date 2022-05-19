package epf.delivery_processes.schema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.delivery_processes.schema.section.Description;
import epf.delivery_processes.schema.section.TeamAllocation;
import epf.delivery_processes.schema.section.WorkBreakdownStructure;
import epf.delivery_processes.schema.section.WorkProductUsage;

/**
 *
 * @author FOXCONN
 */
@Type(DeliveryProcesses.CAPABILITY_PATTERN)
@Schema(name = DeliveryProcesses.CAPABILITY_PATTERN, title = "Capability Pattern")
@Entity(name = DeliveryProcesses.CAPABILITY_PATTERN)
@Table(schema = DeliveryProcesses.SCHEMA, name = "CAPABILITY_PATTERN")
public class CapabilityPattern implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    @Column(name = "SUMMARY")
    private String summary;
	
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
