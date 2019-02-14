package com.zzw.makeup.admin.vo;

import java.util.Date;

public class BrandVO {

	private Long id;
	private String name;// 品牌名称
	private String enName;// 英文名称
	private String logo;// 品牌LOGO
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
