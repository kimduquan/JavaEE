package erp.base.schema.ir.ui;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.users.Users;
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

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}
}
