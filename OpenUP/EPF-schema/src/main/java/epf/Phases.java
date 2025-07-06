package epf;

import epf.delivery_processes.schema.Phase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Phases")
@Entity
@Table(name = "PHASES", schema = "EPF")
public class Phases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "PHASE")
    private Phase phase;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private DeliveryProcesses parentActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryProcesses getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(DeliveryProcesses parentActivities) {
        this.parentActivities = parentActivities;
    }
}
