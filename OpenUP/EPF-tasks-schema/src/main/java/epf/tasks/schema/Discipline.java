package epf.tasks.schema;

import java.util.Set;
import jakarta.json.JsonObject;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;
import jakarta.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(Tasks.DISCIPLINE)
@Schema(name = Tasks.DISCIPLINE, title = "Discipline")
@Entity(name = Tasks.DISCIPLINE)
@Table(schema = Tasks.SCHEMA, name = "DISCIPLINE")
@JsonbPropertyOrder({
    "name",
    "tasks"
})
public class Discipline extends EPFEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
            schema = Tasks.SCHEMA,
            joinColumns = @JoinColumn(name = "DISCIPLINE"),
            inverseJoinColumns = @JoinColumn(name = "TASK"),
            indexes = {@Index(columnList = "DISCIPLINE")}
    )
    private Set<Task> tasks;
    
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
    public Set<Task> getTasks(){
        return tasks;
    }

    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
