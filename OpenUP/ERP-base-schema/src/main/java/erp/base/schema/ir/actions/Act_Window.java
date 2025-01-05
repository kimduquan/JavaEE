package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.act_window.View;
import erp.base.schema.res.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_window")
@Description("Action Window")
public class Act_Window extends Actions {
	
	public enum TargetWindow {
		@Description("Current Window")
		current,
		@Description("New Window")
		new_,
		@Description("Full Screen")
		fullscreen,
		@Description("Main action of Current Window")
		main
	}
	
	public class TargetWindowAttributeConverter extends EnumAttributeConverter<TargetWindow> {
		public TargetWindowAttributeConverter() {
			super(TargetWindow.class, "_", null, null);
		}
	}

	@Column
	@DefaultValue("ir.actions.act_window")
	private String type = "ir.actions.act_window";
	
	@Transient
	private Integer view_id;
	
	@ManyToOne(targetEntity = erp.base.schema.ir.ui.View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "view_id")
	@Description("View Ref.")
	private erp.base.schema.ir.ui.View view;
	
	@Column
	@Description("Domain Value")
	private String domain;
	
	@Column(nullable = false)
	@NotNull
	@Description("Context Value")
	@DefaultValue("{}")
	private String context = "{}";
	
	@Column
	@Description("Record ID")
	private Integer res_id;
	
	@Column(nullable = false)
	@NotNull
	@Description("Destination Model")
	private String res_model;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = TargetWindowAttributeConverter.class)
	@Description("Target Window")
	@DefaultValue("current")
	private TargetWindow target = TargetWindow.current;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("list,form")
	private String view_mode = "list,form";
	
	@Column
	@DefaultValue("kanban")
	private String mobile_view_mode = "kanban";
	
	@Column
	@Description("Action Usage")
	private String usage;
	
	@Transient
	private List<Integer> view_ids;

	@OneToMany(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "act_window_id")
	@Description("No of Views")
	private List<View> views_;
	
	@Transient
	private byte[] views;
	
	@Column
	@DefaultValue("80")
	@Description("Default limit for the list view")
	private Integer limit = 80;
	
	@Transient
	private List<Integer> groups_id;

	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ir_act_window_group_rel", joinColumns = {@JoinColumn(name = "act_id")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Transient
	private Integer search_view_id;
	
	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "search_view_id")
	@Description("Search View Ref.")
	private View search_view;
	
	@Column
	private Boolean filter;
}
