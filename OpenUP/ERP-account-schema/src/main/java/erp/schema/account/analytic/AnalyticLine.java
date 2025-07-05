package erp.schema.account.analytic;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Partner;
import erp.schema.account.Journal;
import erp.schema.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@Entity
@MappedSuperclass
@Description("Analytic Line")
public class AnalyticLine extends Line {
	
	public enum LineCategory {
		@Description("Customer Invoice")
		invoice,
		@Description("Vendor Bill")
		vendor_bill
	}
	
	@Transient
	private Integer product_id;

	@ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	@Description("Product")
	private Product product;
	
	@Transient
	private Integer general_account_id;
	
	@Column
	@ManyToOne(targetEntity = erp.schema.account.Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "general_account_id")
	@Description("Financial Account")
	private erp.schema.account.Account general_account;
	
	@Transient
	private Integer journal_id;
	
	@ManyToOne(targetEntity = Journal.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id", updatable = false)
	@Description("Financial Journal")
	private Journal journal;
	
	@Transient
	private Integer partner_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private Partner partner;
	
	@Transient
	private Integer move_line_id;
	
	@ManyToOne(targetEntity = erp.schema.account.move.Line.class)
	@JoinColumn(name = "move_line_id")
	@Description("Journal Item")
	private erp.schema.account.move.Line move_line;
	
	@Column(length = 8)
	private String code;
	
	@Column
	@Description("Ref.")
	private String ref;
	
	@Column
	@Enumerated(EnumType.STRING)
	private LineCategory category;
}
