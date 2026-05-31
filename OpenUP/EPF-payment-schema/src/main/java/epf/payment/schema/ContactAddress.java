package epf.payment.schema;

public class ContactAddress {

	private String city;
	private String country;
	private String dependentLocality;
	private String organization;
	private String phone;
	private String postalCode;
	private String recipient;
	private String region;
	private String sortingCode;
	private String addressLine;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDependentLocality() {
		return dependentLocality;
	}
	public void setDependentLocality(String dependentLocality) {
		this.dependentLocality = dependentLocality;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSortingCode() {
		return sortingCode;
	}
	public void setSortingCode(String sortingCode) {
		this.sortingCode = sortingCode;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
}
