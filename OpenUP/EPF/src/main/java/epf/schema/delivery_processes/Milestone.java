/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.delivery_processes;

import epf.schema.delivery_processes.section.Properties;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "MILESTONE", schema = "EPF")
public class Milestone {

    @Column(name = "NAME")
    @Id
    private String name;
	
    @JoinColumn(name = "PREDECESSOR", referencedColumnName = "NAME")
    private Iteration predecessor;
	
    @Embedded
    private Properties properties;
    
    @Column(name = "REQUIRED_RESULTS")
    private Boolean requiredResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Iteration predecessor) {
        this.predecessor = predecessor;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Boolean getRequiredResults() {
        return requiredResults;
    }

    public void setRequiredResults(Boolean requiredResults) {
        this.requiredResults = requiredResults;
    }
}
