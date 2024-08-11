package erp.schema.ir.actions;

import org.eclipse.microprofile.graphql.Description;

import erp.schema.View;
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
public class ActWindowView {

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
	@ManyToOne(targetEntity = ActWindow.class)
	@Description("Action")
	private String act_window_id;
	
	/**
	 * 
	 */
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
}
