package erp.schema.ir.ui;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_ui_view_custom")
@Description("Custom View")
public class ViewCustom {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = View.class)
	@NotNull
	@Description("Original View")
	private String ref_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Users.class)
	@NotNull
	@Description("User")
	private String user_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("View Architecture")
	private String arch;
}
