/**
 * 
 */
package openup.schema.work_products;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import openup.schema.OpenUP;

/**
 * @author PC
 *
 */
@Type(OpenUP.DELIVERABLE)
@Schema(name = OpenUP.DELIVERABLE, title = "Deliverable")
@Entity(name = OpenUP.DELIVERABLE)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_DELIVERABLE")
public class Deliverable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/**
	 * 
	 */
	@ManyToOne
    @JoinColumn(name = "DELIVERABLE")
    private epf.work_products.schema.Deliverable deliverable;

	public epf.work_products.schema.Deliverable getDeliverable() {
		return deliverable;
	}

	public void setDeliverable(final epf.work_products.schema.Deliverable deliverable) {
		this.deliverable = deliverable;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
