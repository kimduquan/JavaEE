package erp.schema.account.account;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account_account_tag")
@Description("Account Tag")
public class Tag {
	
	public enum Applicability {
		@Description("Accounts")
		accounts,
		@Description("Taxes")
		taxes,
		@Description("Products")
		products
	}

	@Column(nullable = false)
	@NotNull
	@Description("Tag Name")
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("accounts")
	private String applicability = "accounts";
	
	@Column
	@Description("Color Index")
	private Integer color;
	
	@Column
	@DefaultValue("true")
	@Description("Set active to false to hide the Account Tag without removing it.")
	private Boolean active = true;
	
	@Column
	@Description("Negate Tax Balance")
	private Boolean tax_negate; 
	
	@Transient
	private Integer country_id;
	
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Description("Country")
	private Country country;
}
