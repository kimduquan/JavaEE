package openup.schema.delivery_processes;

import java.io.Serializable;
import javax.persistence.Column;
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
 *
 * @author FOXCONN
 */
@Type(OpenUP.DELIVERY_PROCESS)
@Schema(name = OpenUP.DELIVERY_PROCESS, title = "Delivery Process")
@Entity(name = OpenUP.DELIVERY_PROCESS)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_DELIVERY_PROCESS")
public class DeliveryProcess implements Serializable {

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
    @JoinColumn(name = "DELIVERY_PROCESS")
    private epf.delivery_processes.schema.DeliveryProcess deliveryProcess;
    
    /**
     * 
     */
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;
    
    @Override
    public String toString() {
    	return String.format("%s@%s:%d", getClass().getName(), name, id);
    }

    public epf.delivery_processes.schema.DeliveryProcess getDeliveryProcess() {
        return deliveryProcess;
    }

    public void setDeliveryProcess(final epf.delivery_processes.schema.DeliveryProcess deliveryProcess) {
        this.deliveryProcess = deliveryProcess;
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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
