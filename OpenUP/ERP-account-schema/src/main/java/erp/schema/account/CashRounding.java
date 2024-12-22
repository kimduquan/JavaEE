package erp.schema.account;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Account Cash Rounding")
public class CashRounding {
	
	public enum Strategy {
		@Description("Modify tax amount")
		biggest_tax,
		@Description("Add a rounding line")
		add_invoice_line
	}
	
	public enum RoundingMethod {
		@Description("Up")
		UP,
		@Description("Down")
		DOWN,
		@Description("Nearest")
		HALF_UP
	}

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("0.01")
	@Description("Rounding Precision")
	private Float rounding = 0.01f;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("add_invoice_line")
	@Description("Rounding Strategy")
	private Strategy strategy = Strategy.add_invoice_line;
	
	@Transient
	private Integer profit_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "profit_account_id")
	@Description("Profit Account")
	private Account profit_account;
	
	@Transient
	private Integer loss_account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "loss_account_id")
	@Description("Loss Account")
	private Account loss_account;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("HALF-UP")
	@Description("Rounding Method")
	private RoundingMethod rounding_method = RoundingMethod.HALF_UP;
}
