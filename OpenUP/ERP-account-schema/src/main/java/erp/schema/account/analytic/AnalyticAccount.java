package erp.schema.account.analytic;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class AnalyticAccount extends Account {

	@Column
	@Description("Invoice Count")
	private Integer invoice_count;
	
	@Column
	@Description("Vendor Bill Count")
	private Integer vendor_bill_count;
	
	@Column
	private String debit;
	
	@Column
	private String credit;
}
