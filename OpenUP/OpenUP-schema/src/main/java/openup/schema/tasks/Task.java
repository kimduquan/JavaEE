/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema.tasks;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import openup.schema.OpenUP;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.TASK)
@Schema(name = OpenUP.TASK, title = "Task")
@Entity(name = OpenUP.TASK)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_TASK")
public class Task implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "TASK")
    private epf.tasks.schema.Task task;
    
    /**
     * 
     */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;

    public epf.tasks.schema.Task getTask() {
        return task;
    }

    public void setTask(final epf.tasks.schema.Task task) {
        this.task = task;
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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
