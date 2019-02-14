package com.zzw.makeup.admin.form.user;

import com.zzw.makeup.admin.entity.User;
import com.zzw.makeup.admin.form.RequestPageBase;

public class UserPageForm extends RequestPageBase<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7529920669894258757L;

	private String username;

	protected UserPageForm() {
		super(User.class);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
