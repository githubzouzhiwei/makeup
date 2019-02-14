package com.zzw.makeup.admin.form.brand;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public class BrandEditForm {

	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String enName;
	@NotBlank
	@URL
	private String logo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
