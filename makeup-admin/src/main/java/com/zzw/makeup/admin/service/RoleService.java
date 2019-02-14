package com.zzw.makeup.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zzw.makeup.admin.dao.RoleDAO;
import com.zzw.makeup.admin.entity.Role;
import com.zzw.makeup.admin.form.role.RolePageForm;

@Service
public class RoleService {

	@Autowired
	private RoleDAO roleDAO;

	public Page<Role> findAll(RolePageForm form) {
		PageRequest pageRequest = PageRequest.of(form.getPageNo() - 1, form.getPageSize(),
				Sort.by(Direction.DESC, "id"));
		return roleDAO.findAll(pageRequest);
	}

	public Role findByName(String name) {
		return roleDAO.findByName(name);
	}

	public Role findById(long id) {
		return roleDAO.findById(id).get();
	}

	public void save(Role entity) {
		roleDAO.save(entity);
	}

}
