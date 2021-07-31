/**
 * 
 */
package openup.schema.work_products;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

/**
 * @author PC
 *
 */
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
    private Long deliverableId;
	
	/**
	 * 
	 */
	@ManyToOne
    @JoinColumn(name = "DELIVERABLE")
    private epf.schema.work_products.Deliverable deliverable;
	
	/**
	 * 
	 */
	@OneToOne
    @MapsId
    @JoinColumn(name = "ARTIFACT")
	private Artifact artifact;

	public Long getDeliverableId() {
		return deliverableId;
	}

	public void setDeliverableId(final Long deliverableId) {
		this.deliverableId = deliverableId;
	}

	public epf.schema.work_products.Deliverable getDeliverable() {
		return deliverable;
	}

	public void setDeliverable(final epf.schema.work_products.Deliverable deliverable) {
		this.deliverable = deliverable;
	}
	
	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(final Artifact artifact) {
		this.artifact = artifact;
	}
}
