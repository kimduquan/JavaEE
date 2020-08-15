/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.model.delivery_processes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author FOXCONN
 */
@MappedSuperclass
public class Properties implements Serializable {
    
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

    public void setEventDriven(Boolean eventDriven) {
        this.eventDriven = eventDriven;
    }

    public Boolean getMultipleOccurrences() {
        return multipleOccurrences;
    }

    public void setMultipleOccurrences(Boolean multipleOccurrences) {
        this.multipleOccurrences = multipleOccurrences;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Boolean getPlanned() {
        return planned;
    }

    public void setPlanned(Boolean planned) {
        this.planned = planned;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }
}
