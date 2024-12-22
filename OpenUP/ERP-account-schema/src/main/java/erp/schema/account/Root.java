package erp.schema.account;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "account_root")
@Description("Account codes first 2 digits")
public class Root {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Root.class)
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	private String company_id;
}
