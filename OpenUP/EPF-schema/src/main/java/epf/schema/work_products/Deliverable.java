/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@Type(EPF.Deliverable)
@Schema(name = EPF.Deliverable, title = "Deliverable")
@Entity(name = EPF.Deliverable)
@Table(schema = EPF.Schema, name = "DELIVERABLE")
public class Deliverable {
    
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
    @OneToOne
    @MapsId
    @JoinColumn(name = "NAME")
    @NotNull
    private Artifact artifact;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(final Artifact artifact) {
        this.artifact = artifact;
    }
}
