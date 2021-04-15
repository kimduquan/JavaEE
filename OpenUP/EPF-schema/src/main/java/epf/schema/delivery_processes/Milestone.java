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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.MILESTONE)
@Schema(name = EPF.MILESTONE, title = "Milestone")
@Entity(name = EPF.MILESTONE)
@Table(schema = EPF.SCHEMA, name = "MILESTONE")
public class Milestone {

    /**
     * 
     */
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
	
    /**
     * 
     */
    @JoinColumn(name = "PREDECESSOR")
    private Iteration predecessor;
	
    /**
     * 
     */
    @Embedded
    @NotNull
    private Properties properties;
    
    /**
     * 
     */
    @Column(name = "REQUIRED_RESULTS")
    private Boolean requiredResults;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public Iteration getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(final Iteration predecessor) {
        this.predecessor = predecessor;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public Boolean getRequiredResults() {
        return requiredResults;
    }

    public void setRequiredResults(final Boolean requiredResults) {
        this.requiredResults = requiredResults;
    }
}
