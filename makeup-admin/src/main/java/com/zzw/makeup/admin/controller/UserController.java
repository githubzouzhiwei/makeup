package com.zzw.makeup.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zzw.makeup.admin.entity.User;
import com.zzw.makeup.admin.form.user.UserEditForm;
import com.zzw.makeup.admin.form.user.UserPageForm;
import com.zzw.makeup.admin.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	private static final String navTabId = "list-user";
	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	public String list(UserPageForm form, HttpServletRequest request) {
		Page<User> page = userService.page(form);
		request.setAttribute("page", page);
		request.setAttribute("form", form);
		return "admin/user/list";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String toCreate() {
		return "admin/user/edit";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	public void create(UserEditForm form, HttpServletResponse response) {
		String username = form.getUsername().trim();
		User entity = userService.findByUsername(username);
		if (entity != null) {
			callback(response, 300, "用户已存在", null, null);
			return;
		}
		entity = new User();
		entity.setUsername(username);
		entity.setPassword(new BCryptPasswordEncoder().encode("123456"));
		userService.save(entity);
		callback(response, 200, "创建成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String toUpdate(Long id, HttpServletRequest request) {
		if (id == null || id <= 0) {
			return null;
		}
		User entity = userService.findById(id);
		if (entity == null) {
			return null;
		}
		request.setAttribute("entity", entity);
		return "admin/user/edit";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update(UserEditForm form, HttpServletResponse response) {
		Long id = form.getId();
		if (id == null || id <= 0) {
			return;
		}
		User entity = userService.findById(id);
		if (entity == null) {
			return;
		}
		// 判断用户名称是否已存在
		String username = form.getUsername().trim();
		User tmp = userService.findByUsername(username);
		if (tmp != null && tmp.getId() != id) {
			callback(response, 300, "用户已存在", null, null);
			return;
		}
		// 更新用户
		entity.setUsername(username);
		userService.save(entity);
		callback(response, 200, "修改成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST)
	public void resetPassword(@NotNull Long id, HttpServletResponse response) {
		if (id <= 0) {
			return;
		}
		User entity = userService.findById(id);
		if (entity == null) {
			return;
		}
		entity.setPassword(new BCryptPasswordEncoder().encode("123456"));
		userService.save(entity);
		callback(response, 200, "重置成功", navTabId, "closeCurrent");
	}

}
