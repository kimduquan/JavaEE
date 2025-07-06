package epf.delivery_processes.schema.section;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

@Type
@Embeddable
public class Properties implements Serializable {

    private static final long serialVersionUID = 1L;

	@Column(name = "EVENT_DRIVEN")
    private Boolean eventDriven;
    
    @Column(name = "MULTIPLE_OCCURRENCES")
    private Boolean multipleOccurrences;
    
    @Column(name = "ONGOING")
    private Boolean ongoing;
    
    @Column(name = "OPTIONAL")
    private Boolean optional;
    
    @Column(name = "PLANNED")
    private Boolean planned;
    
    @Column(name = "REPEATABLE")
    private Boolean repeatable;
    
    public Boolean getEventDriven() {
        return eventDriven;
    }

    public void setEventDriven(final Boolean eventDriven) {
        this.eventDriven = eventDriven;
    }

    public Boolean getMultipleOccurrences() {
        return multipleOccurrences;
    }

    public void setMultipleOccurrences(final Boolean multipleOccurrences) {
        this.multipleOccurrences = multipleOccurrences;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(final Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(final Boolean optional) {
        this.optional = optional;
    }

    public Boolean getPlanned() {
        return planned;
    }

    public void setPlanned(final Boolean planned) {
        this.planned = planned;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(final Boolean repeatable) {
        this.repeatable = repeatable;
    }
}
