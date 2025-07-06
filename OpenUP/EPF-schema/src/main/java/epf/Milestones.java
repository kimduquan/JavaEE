package epf;

import epf.delivery_processes.schema.Milestone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Milestones")
@Entity
@Table(name = "MILESTONES", schema = "EPF")
public class Milestones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MILESTONE")
    private Milestone milestone;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "PREDECESSOR")
    private Iterations predecessor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterations getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Iterations predecessor) {
        this.predecessor = predecessor;
    }
}
