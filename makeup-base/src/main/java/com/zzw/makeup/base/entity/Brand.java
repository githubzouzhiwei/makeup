package com.zzw.makeup.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 品牌
 * 
 * @author zouzhiwei
 * @since 2018年9月7日 下午3:17:25
 */
@Entity
@Table(name = "m_brand")
public class Brand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3959966440346270785L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;// 品牌名称
	private String enName;// 英文名称
	private String logo;// 品牌LOGO
	private int status;// 状态（-2：删除 -1：审核不通过 0：待审 1：审核通过）
	private Date createAt;// 创建时间
	private long createBy;// 创建用户
	private Date updateAt;// 修改时间
	private long updateBy;// 修改用户
	private Date censorAt;// 审核时间
	private long censorBy;// 审核用户

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

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCensorAt() {
		return censorAt;
	}

	public void setCensorAt(Date censorAt) {
		this.censorAt = censorAt;
	}

	public long getCensorBy() {
		return censorBy;
	}

	public void setCensorBy(long censorBy) {
		this.censorBy = censorBy;
	}

}
