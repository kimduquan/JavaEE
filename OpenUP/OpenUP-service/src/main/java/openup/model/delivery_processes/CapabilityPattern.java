/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.model.delivery_processes;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "CapabilityPattern",
        title = "Capability Pattern"
)
@Entity
@Table(name = "CAPABILITY_PATTERN")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TEMPLATE", length = 63)
public class CapabilityPattern extends Properties {
    
    @Column(name = "TEMPLATE")
    @Id
    private String template;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
