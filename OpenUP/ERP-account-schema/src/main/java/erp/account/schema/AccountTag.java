package erp.account.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.res.Company;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "account_account_tag")
@Description("Account Tag")
@NodeEntity("Account Tag")
public class AccountTag {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Tag Name")
	@Convert(NameAttributeConverter.class)
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("accounts")
	@Property
	private String applicability = "accounts";
	
	/**
	 * 
	 */
	@Column
	@Description("Color Index")
	@Property
	private Integer color;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Set active to false to hide the Account Tag without removing it.")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Negate Tax Balance")
	@Property
	private Boolean tax_negate; 
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Country")
	@Property
	private String country_id;
}
