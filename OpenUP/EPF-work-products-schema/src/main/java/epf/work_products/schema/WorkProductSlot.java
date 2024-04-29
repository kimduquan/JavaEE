package epf.work_products.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;

/**
 *
 * @author FOXCONN
 */
@Type(WorkProducts.WORK_PRODUCT_SLOT)
@Schema(name = WorkProducts.WORK_PRODUCT_SLOT, title = "Work Product Slot")
@Entity(name = WorkProducts.WORK_PRODUCT_SLOT)
@Table(schema = WorkProducts.SCHEMA, name = "WORK_PRODUCT_SLOT")
public class WorkProductSlot extends EPFEntity {
	
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
}
