package erp.schema.account;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.product.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

/**
 * 
 */
@Entity
@MappedSuperclass
@Description("Analytic Plan's Applicabilities")
public class AnalyticApplicability extends erp.schema.analytic.AnalyticApplicability {

	@Column
	@Enumerated(EnumType.STRING)
	private String business_domain;
	
	@Column
	@Description("Financial Accounts Prefix")
	private String account_prefix;
	
	@Column
	@ManyToOne(targetEntity = Category.class)
	@Description("Product Category")
	private String product_categ_id;
	
	@Column
	@Description("Defines if the field account prefix should be displayed")
	private Boolean display_account_prefix;
}
