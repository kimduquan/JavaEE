package erp.schema.ir.actions;

import java.util.List;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.schema.Groups;
import erp.schema.View;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_window")
@Description("Action Window")
public class ActWindow extends Actions {

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.act_window")
	private String type = "ir.actions.act_window";
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("View Ref.")
	private String view_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Domain Value")
	private String domain;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Context Value")
	@DefaultValue("{}")
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column
	@Description("Record ID")
	private Integer res_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Destination Model")
	private String res_model;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Target Window")
	@DefaultValue("current")
	private String target = "current";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("")
	@DefaultValue("tree,form")
	private String view_mode = "tree,form";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("kanban")
	@Description("First view mode in mobile and small screen environments (default='kanban'). If it can't be found among available view modes, the same mode as for wider screens is used)")
	private String mobile_view_mode = "kanban";
	
	/**
	 * 
	 */
	@Column
	@Description("Action Usage")
	private String usage;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = ActWindowView.class)
	@ElementCollection(targetClass = ActWindowView.class)
	@CollectionTable(name = "ir_actions_act_window_view", joinColumns = {
			@JoinColumn(referencedColumnName = "act_window_id")
	})
	@Description("No of Views")
	private List<String> view_ids;
	
	/**
	 * 
	 */
	@Column
	@Description(
			"This function field computes the ordered list of views that should be enabled " +
            "when displaying the result of an action, federating view mode, views and " +
            "reference view. The result is returned as an ordered list of pairs (view_id,view_mode)."
            )
	private byte[] views;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("80")
	@Description("Default limit for the list view")
	private Integer limit = 80;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(joinColumns = {
			@JoinColumn(name = "act_id", referencedColumnName = "gid")
	})
	@Description("Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Search View Ref.")
	private String search_view_id;
	
	/**
	 * 
	 */
	@Column
	private Boolean filter;
}
