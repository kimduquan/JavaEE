package erp.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_window_view")
@Description("Action Window View")
public class IrActionsActWindowView {

	/**
	 * 
	 */
	@Column
	private Integer sequence;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("View")
	private String view_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("View Type")
	private String view_mode;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = IrActionsActWindow.class)
	@Description("Action")
	private String act_window_id;
	
	/**
	 * 
	 */
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
}
