/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.tasks;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Discipline",
        title = "Discipline"
)
@Entity
@Table(name = "DISCIPLINE")
public class Discipline implements Serializable {
    
    @Column(name = "NAME")
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "TASKS",
            joinColumns = @JoinColumn(
                    name = "DISCIPLINE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "TASK"
            )
    )
    private List<Task> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Name("Tasks")
    public List<Task> getTasks(){
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
