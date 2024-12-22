package erp.schema.account.analytic.distribution;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.product.Category;
import erp.schema.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class DistributionModel extends Model {

	@Column
	@Description("Accounts Prefix")
	private String account_prefix;
	
	@Transient
	private Integer product_id;
	
	@ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	@Description("Product")
	private Product product;
	
	@Transient
	private Integer product_categ_id;
	
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_categ_id")
	@Description("Product Category")
	private Category product_categ;
	
	@Column
	private String prefix_placeholder;
}
