package epf.tasks.schema;

import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;
import javax.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.DISCIPLINE)
@Schema(name = EPF.DISCIPLINE, title = "Discipline")
@Entity(name = EPF.DISCIPLINE)
@Table(schema = EPF.TASKS_SCHEMA, name = "DISCIPLINE")
@JsonbPropertyOrder({
    "name",
    "tasks"
})
@NamedQuery(
        name = Discipline.DISCIPLINES, 
        query = "SELECT d FROM EPF_Discipline AS d")
public class Discipline implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    public static final String DISCIPLINES = "EPF_Discipline.Disciplines";

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
    @Column(name = "SUMMARY")
    private String summary;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "TASKS",
            schema = EPF.TASKS_SCHEMA,
            joinColumns = @JoinColumn(name = "DISCIPLINE"),
            inverseJoinColumns = @JoinColumn(name = "TASK"),
            indexes = {@Index(columnList = "DISCIPLINE")}
    )
    private List<Task> tasks;
    
    /**
     * 
     */
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
    
    @Name("Tasks")
    public List<Task> getTasks(){
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
