package openup.schema.delivery_processes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Type(OpenUP.ACTIVITY)
@Schema(name = OpenUP.ACTIVITY, title = "Activity")
@Entity(name = OpenUP.ACTIVITY)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_ACTIVITY", indexes = {@Index(columnList = "PARENT_ACTIVITIES")})
public class Activity implements Serializable {

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
    @JoinColumn(name = "ACTIVITY")
    private epf.delivery_processes.schema.Activity activity;
    
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
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ACTIVITIES")
    private Iteration parentActivities;
    
    @Override
    public String toString() {
    	return String.format("%s@%s:%d", getClass().getName(), name, id);
    }

    public epf.delivery_processes.schema.Activity getActivity() {
        return activity;
    }

    public void setActivity(final epf.delivery_processes.schema.Activity activity) {
        this.activity = activity;
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

    public Iteration getParentActivities() {
        return parentActivities;
    }

    public void setParentActivities(final Iteration parentActivities) {
        this.parentActivities = parentActivities;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
}
