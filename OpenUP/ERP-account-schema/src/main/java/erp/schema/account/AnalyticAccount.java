package erp.schema.account;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@MappedSuperclass
@Table(name = "account_analytic_account")
public class AnalyticAccount extends erp.schema.analytic.AnalyticAccount {

	/**
	 * 
	 */
	@Column
	@Description("Invoice Count")
	private Integer invoice_count;
	
	/**
	 * 
	 */
	@Column
	@Description("Vendor Bill Count")
	private Integer vendor_bill_count;
	
	/**
	 * 
	 */
	@Column
	private String debit;
	
	/**
	 * 
	 */
	@Column
	private String credit;
}
