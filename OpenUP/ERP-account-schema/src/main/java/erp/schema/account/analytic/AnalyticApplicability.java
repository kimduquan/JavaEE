package erp.schema.account.analytic;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.product.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
@Description("Analytic Plan's Applicabilities")
public class AnalyticApplicability extends Applicability {

	public enum BussinessDomain {
		@Description("Invoice")
		invoice,
		@Description("Vendor Bill")
		bill
	}
	
	@Column
	@Enumerated(EnumType.STRING)
	private BussinessDomain business_domain;
	
	@Column
	@Description("Financial Accounts Prefix")
	private String account_prefix;
	
	@Transient
	private Integer product_categ_id;
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_categ_id")
	@Description("Product Category")
	private Category product_categ;
	
	@Column
	private Boolean display_account_prefix;
}
