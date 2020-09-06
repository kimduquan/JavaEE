/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.delivery_processes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
        name = "Milestone",
        title = "Milestone"
)
@Entity
@Table(name = "MILESTONE")
public class Milestone extends Properties {
    
    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "REQUIRE_RESULTS")
    private Boolean requiredResults;
    
    @OneToOne
    @JoinColumn(name = "PREDECESSOR", referencedColumnName = "NAME")
    private Iteration predecessor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequiredResults() {
        return requiredResults;
    }

    public void setRequiredResults(Boolean requiredResults) {
        this.requiredResults = requiredResults;
    }

    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Iteration predecessor) {
        this.predecessor = predecessor;
    }
}
