package com.zzw.makeup.admin.form.product;

import com.zzw.makeup.admin.form.RequestPageBase;
import com.zzw.makeup.base.entity.Product;

public class ProductPageForm extends RequestPageBase<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6461221792737653977L;

	protected ProductPageForm() {
		super(Product.class);
	}

	private Long id;// 产品ID
	private String name;// 产品名称
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
