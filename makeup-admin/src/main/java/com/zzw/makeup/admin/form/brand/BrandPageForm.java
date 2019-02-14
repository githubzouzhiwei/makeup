package com.zzw.makeup.admin.form.brand;

import com.zzw.makeup.admin.form.RequestPageBase;
import com.zzw.makeup.base.entity.Brand;

public class BrandPageForm extends RequestPageBase<Brand> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3784888898612348542L;

	protected BrandPageForm() {
		super(Brand.class);
	}

	private Long id;// 品牌ID
	private String name;// 品牌名称
	private Integer status;// 状态

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
