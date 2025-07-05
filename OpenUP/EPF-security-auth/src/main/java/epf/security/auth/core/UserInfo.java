package epf.security.auth.core;

public class UserInfo {
	
	private String sub;
	
	private String name;
	
	private String given_name;
	
	private String family_name;
	
	private String middle_name;
	
	private String nickname;
	
	private String preferred_username;
	
	private String profile;
	
	private String picture;
	
	private String website;
	
	private String email;
	
	private Boolean email_verified;
	
	private String gender;
	
	private String birthdate;
	
	private String zoneinfo;
	
	private String locale;
	
	private String phone_number;
	
	private Boolean phone_number_verified;
	
	private AddressClaim address;
	
	private Long updated_at;
	
	public String getSub() {
		return sub;
	}
	public void setSub(final String sub) {
		this.sub = sub;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(final String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(final String family_name) {
		this.family_name = family_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(final String middle_name) {
		this.middle_name = middle_name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}
	public String getPreferred_username() {
		return preferred_username;
	}
	public void setPreferred_username(final String preferred_username) {
		this.preferred_username = preferred_username;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(final String profile) {
		this.profile = profile;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(final String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	public Boolean getEmail_verified() {
		return email_verified;
	}
	public void setEmail_verified(final Boolean email_verified) {
		this.email_verified = email_verified;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(final String gender) {
		this.gender = gender;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(final String birthdate) {
		this.birthdate = birthdate;
	}
	public String getZoneinfo() {
		return zoneinfo;
	}
	public void setZoneinfo(final String zoneinfo) {
		this.zoneinfo = zoneinfo;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(final String locale) {
		this.locale = locale;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(final String phone_number) {
		this.phone_number = phone_number;
	}
	public Boolean getPhone_number_verified() {
		return phone_number_verified;
	}
	public void setPhone_number_verified(final Boolean phone_number_verified) {
		this.phone_number_verified = phone_number_verified;
	}
	public AddressClaim getAddress() {
		return address;
	}
	public void setAddress(final AddressClaim address) {
		this.address = address;
	}
	public Long getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(final Long updated_at) {
		this.updated_at = updated_at;
	}
}
