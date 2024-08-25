package erp.account.schema;

import org.eclipse.microprofile.graphql.Description;
import erp.schema.product.Product;
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
@Description("Analytic Line")
public class AnalyticLine extends erp.schema.analytic.AnalyticLine {

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
	@ManyToOne(targetEntity = Account.class)
	@Description("Financial Account")
	private String general_account_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Journal.class)
	@Description("Financial Journal")
	private String journal_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne
	private String partner_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = MoveLine.class)
	private String move_line_id;
	
	/**
	 * 
	 */
	@Column(length = 8)
	private String code;
	
	/**
	 * 
	 */
	@Column
	@Description("Ref.")
	private String ref;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private String category;
}
