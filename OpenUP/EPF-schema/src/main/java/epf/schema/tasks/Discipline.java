/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.tasks;

import java.util.List;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;
import javax.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Discipline)
@Schema(name = EPF.Discipline, title = "Discipline")
@Entity(name = EPF.Discipline)
@Table(schema = EPF.Schema, name = "DISCIPLINE")
@JsonbPropertyOrder({
    "name",
    "tasks"
})
@NamedQuery(
        name = Discipline.DISCIPLINES, 
        query = "SELECT d FROM EPF_Discipline AS d")
public class Discipline {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToMany
    @JoinTable(
            name = "TASKS",
            schema = EPF.Schema,
            joinColumns = @JoinColumn(name = "DISCIPLINE"),
            inverseJoinColumns = @JoinColumn(name = "TASK"),
            indexes = {@Index(columnList = "DISCIPLINE")}
    )
    private List<Task> tasks;
    
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    public static final String DISCIPLINES = "EPF_Discipline.Disciplines";

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
    
    @Name("Tasks")
    public List<Task> getTasks(){
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
