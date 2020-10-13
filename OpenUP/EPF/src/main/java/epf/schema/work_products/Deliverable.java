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
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(title = "Deliverable")
@Entity
@Table(name = "DELIVERABLE", schema = "EPF")
public class Deliverable {
    
    @Column(name = "NAME")
    @Id
    private String name;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "NAME")
    private Artifact artifact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }
}
