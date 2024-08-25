package erp.account.schema;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.product.Category;
import erp.schema.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

/**
 * 
 */
@Entity
@MappedSuperclass
public class AnalyticDistributionModel extends erp.schema.analytic.AnalyticDistributionModel {

	/**
	 * 
	 */
	@Column
	@Description("Accounts Prefix")
	private String account_prefix;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Product.class)
	@Description("Product")
	private String product_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Category.class)
	@Description("Product Category")
	private String product_categ_id;
}
