package erp.base.schema.res;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.res.country.Country;
import erp.base.schema.res.country.State;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_bank")
@Description("Bank")
@NodeEntity("Bank")
public class Bank {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String street;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String street2;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String zip;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String city;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = State.class)
	@Description("Fed. State")
	@Property
	@Relationship(type = "STATE")
	private String state;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Country.class)
	@Property
	@Relationship(type = "COUNTRY")
	private String country;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String email;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String phone;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Bank Identifier Code")
	@Property
	private String bic;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}
}
