package erp.base.schema.ir.actions.act_window;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Act_Window;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity(name = "WindowView")
@Table(name = "ir_act_window_view")
@Description("Action Window View")
public class View {
	
	public enum ViewType {
		@Description("List")
		list,
		@Description("Form")
	    form,
	    @Description("Graph")
	    graph,
	    @Description("Pivot")
	    pivot,
	    @Description("Calendar")
	    calendar,
	    @Description("Kanban")
	    kanban,
	}
	
	@Id
	private int id;

	@Column
	private Integer sequence;
	
	@Transient
	private Integer view_id;

	@ManyToOne(targetEntity = erp.base.schema.ir.ui.View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "view_id")
	@Description("View")
	private erp.base.schema.ir.ui.View view;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("View Type")
	private ViewType view_mode;
	
	@Transient
	private Integer act_window_id;
	
	@ManyToOne(targetEntity = Act_Window.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "act_window_id")
	@Description("Action")
	private Act_Window act_window;
	
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
}
