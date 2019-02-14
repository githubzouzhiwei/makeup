package com.zzw.makeup.admin.vo;

import java.util.Date;

public class ProductVO {

	private Long id;
	private String brandName;// 品牌
	private String name;// 产品名称
	private String[] images;// 产品图片
	private int status;// 状态（-2：删除 -1：审核不通过 0：待审 1：审核通过）
	private Date createAt;// 创建时间
	private String createUsername;// 创建用户
	private Date censorAt;// 审核时间
	private String censorUsername;// 审核用户

	/**
	 * 状态名称
	 */
	public String getStatusName() {
		if (status == -2) {
			return "删除";
		} else if (status == -1) {
			return "不通过";
		} else if (status == 0) {
			return "待审";
		} else if (status == 1) {
			return "通过";
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public Date getCensorAt() {
		return censorAt;
	}

	public void setCensorAt(Date censorAt) {
		this.censorAt = censorAt;
	}

	public String getCensorUsername() {
		return censorUsername;
	}

	public void setCensorUsername(String censorUsername) {
		this.censorUsername = censorUsername;
	}

}
