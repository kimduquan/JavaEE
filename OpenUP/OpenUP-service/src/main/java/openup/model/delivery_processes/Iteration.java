/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.model.delivery_processes;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Iteration",
        title = "Iteration"
)
@Entity
@Table(name = "ITERATION")
public class Iteration extends CapabilityPattern {
    
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @Column(name = "NUMBER")
    private Integer number;
    
    @OneToOne
    @JoinColumn(name = "PARENT_ACTIVITIES", referencedColumnName = "NAME")
    private Phase parentActivities;
    
    @ManyToMany
    @JoinTable(name = "ITERATION_ACTIVITIES",
            joinColumns = {@JoinColumn(name = "ITERATION")},
            inverseJoinColumns = {@JoinColumn(name = "ACTIVITY")}
    )
    private List<Activity> activities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Phase getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(Phase parentActivities) {
        this.parentActivities = parentActivities;
    }
}
