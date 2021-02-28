package epf.dto;

import java.io.Serializable;

public class Province implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3055995992262228819L;

	// JavaBean Properties
	private long provinceId;
	private String provinceName;

	public Province(long provinceId, String provinceName) {
		this.provinceId = provinceId;
		this.provinceName = provinceName;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
