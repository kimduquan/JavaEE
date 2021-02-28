package epf.dto;

import java.io.Serializable;

public class City implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8456545299154327761L;

	// JavaBean Properties
	private long cityId;
	private String cityName;
	private String postalCode;
	private long provinceId;

	public City(long cityId, long provinceId, String cityName, String postalCode) {
		this.cityId = cityId;
		this.provinceId = provinceId;
		this.cityName = cityName;
		this.postalCode = postalCode;
	}

	public long getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
}
