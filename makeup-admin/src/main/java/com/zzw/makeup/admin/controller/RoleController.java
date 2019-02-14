package com.zzw.makeup.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zzw.makeup.admin.entity.Role;
import com.zzw.makeup.admin.form.role.RoleEditForm;
import com.zzw.makeup.admin.form.role.RolePageForm;
import com.zzw.makeup.admin.service.RoleService;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	private static final String navTabId = "list-role";
	@Autowired
	private RoleService roleService;

	@RequestMapping("/list.do")
	public String list(RolePageForm form, HttpServletRequest request) {
		Page<Role> page = roleService.findAll(form);
		request.setAttribute("page", page);
		request.setAttribute("form", form);
		return "admin/role/list";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String toCreate() {
		return "admin/role/edit";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	public void create(RoleEditForm form, HttpServletResponse response) {
		String name = form.getName().trim();
		Role entity = roleService.findByName(name);
		if (entity != null) {
			callback(response, 300, "角色已存在", null, null);
			return;
		}
		entity = new Role();
		entity.setName(name);
		roleService.save(entity);
		callback(response, 200, "创建成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String toUpdate(Long id, HttpServletRequest request) {
		if (id == null || id <= 0) {
			return null;
		}
		Role entity = roleService.findById(id);
		if (entity == null) {
			return null;
		}
		request.setAttribute("entity", entity);
		return "admin/role/edit";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update(RoleEditForm form, HttpServletResponse response) {
		Long id = form.getId();
		if (id == null || id <= 0) {
			return;
		}
		Role entity = roleService.findById(id);
		if (entity == null) {
			return;
		}
		// 判断角色名称是否已存在
		String name = form.getName().trim();
		Role tmp = roleService.findByName(name);
		if (tmp != null && tmp.getId() != id) {
			callback(response, 300, "角色已存在", null, null);
			return;
		}
		// 更新角色
		entity.setName(name);
		roleService.save(entity);
		callback(response, 200, "修改成功", navTabId, "closeCurrent");
	}

}
