package com.zzw.makeup.admin.form;

import java.io.Serializable;

public abstract class RequestPageBase<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7999737352570996142L;

	/**
	 * type
	 */
	protected Class<T> type;

	/**
	 * 
	 * @param type
	 */
	protected RequestPageBase(Class<T> type) {
		this.type = type;
	}

	public static final String ORDER_TOKEN = ",";

	private int pageNo = 1;

	private int pageSize = 25;

	private String orderField = null;

	private String orderDirection = "desc";

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		if (orderDirection.toLowerCase().equals("asc") || orderDirection.toLowerCase().equals("desc")) {
			this.orderDirection = orderDirection;
		}
	}

}
