package erp.account.schema;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "account_group")
@Description("Account Group")
public class Group {
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Group.class)
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	private String parent_path;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String code_prefix_start;
	
	/**
	 * 
	 */
	@Column
	private String code_prefix_end;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Company.class)
	@NotNull
	private String company_id;
}
