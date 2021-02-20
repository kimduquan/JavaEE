/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Template)
@Schema(name = EPF.Template, title = "Template")
@Entity(name = EPF.Template)
@Table(schema = EPF.Schema, name = "TEMPLATE")
public class Template {
	
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
