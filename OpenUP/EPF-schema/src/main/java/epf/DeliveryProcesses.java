package epf;

import epf.delivery_processes.schema.DeliveryProcess;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Delivery_Processes")
@Entity
@Table(name = "DELIVERY_PROCESSES", schema = "EPF")
public class DeliveryProcesses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "DELIVERY_PROCESS")
    private DeliveryProcess deliveryProcess;
    
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryProcess getDeliveryProcess() {
        return deliveryProcess;
    }

    public void setDeliveryProcess(DeliveryProcess deliveryProcess) {
        this.deliveryProcess = deliveryProcess;
    }

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
}
