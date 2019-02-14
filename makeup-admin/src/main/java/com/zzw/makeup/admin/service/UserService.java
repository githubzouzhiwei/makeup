package com.zzw.makeup.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zzw.makeup.admin.dao.UserDAO;
import com.zzw.makeup.admin.entity.User;
import com.zzw.makeup.admin.form.user.UserPageForm;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		return user;
	}

	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@SuppressWarnings("serial")
	public Page<User> page(UserPageForm form) {
		Specification<User> specification = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (form.getUsername() != null && !"".equals(form.getUsername())) {
					Predicate p = criteriaBuilder.like(root.get("username"), "%" + form.getUsername() + "%");
					predicateList.add(p);
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
			}
		};
		PageRequest pageRequest = PageRequest.of(form.getPageNo() - 1, form.getPageSize(),
				Sort.by(Direction.DESC, "id"));
		return userDAO.findAll(specification, pageRequest);
	}

	public User findById(long id) {
		if (id <= 0) {
			return null;
		}
		return userDAO.findById(id).get();
	}

	public void save(User user) {
		userDAO.save(user);
	}
}
