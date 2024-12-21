package erp.base.schema.ir.ui.view;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_ui_view_custom")
@Description("Custom View")
public class Custom {
	
	@Id
	private int id;

	@Transient
	private Integer ref_id;
	
	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_id", nullable = false)
	@NotNull
	@Description("Original View")
	private View ref;
	
	@Transient
	private Integer user_id;
	
	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	@Description("User")
	private Users user;
	
	@Column(nullable = false)
	@NotNull
	@Description("View Architecture")
	private String arch;
}
