package epf.delivery_processes.schema;

import epf.schema.h2.Queries;
import epf.schema.h2.QueryNames;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
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

/**
 *
 * @author FOXCONN
 */
@Type(EPF.DELIVERY_PROCESS)
@Schema(name = EPF.DELIVERY_PROCESS, title = "Delivery Process")
@Entity(name = EPF.DELIVERY_PROCESS)
@Table(schema = EPF.SCHEMA, name = "DELIVERY_PROCESS")
@NamedQuery(
        name = DeliveryProcess.DELIVERY_PROCESSES, 
        query = "SELECT dp FROM EPF_DeliveryProcess AS dp"
)
@NamedNativeQuery(name = QueryNames.FT_SEARCH_DATA, query = Queries.FT_SEARCH_DATA)
public class DeliveryProcess implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    public static final String DELIVERY_PROCESSES = "EPF_DeliveryProcess.DeliveryProcesses";

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
