package epf.security.auth.core;

public class AddressClaim {

	private String formatted;
	
	private String street_address;
	
	private String locality;
	
	private String region;
	
	private String postal_code;
	
	private String country;
	
	public String getFormatted() {
		return formatted;
	}
	public void setFormatted(final String formatted) {
		this.formatted = formatted;
	}
	public String getStreet_address() {
		return street_address;
	}
	public void setStreet_address(final String street_address) {
		this.street_address = street_address;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(final String locality) {
		this.locality = locality;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(final String region) {
		this.region = region;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(final String postal_code) {
		this.postal_code = postal_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
}
