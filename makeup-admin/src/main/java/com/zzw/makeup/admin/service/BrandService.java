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
import org.springframework.stereotype.Service;

import com.zzw.makeup.admin.dao.UserDAO;
import com.zzw.makeup.admin.entity.User;
import com.zzw.makeup.admin.form.brand.BrandPageForm;
import com.zzw.makeup.admin.vo.BrandVO;
import com.zzw.makeup.admin.vo.Pager;
import com.zzw.makeup.base.dao.BrandDAO;
import com.zzw.makeup.base.entity.Brand;

@Service
public class BrandService {

	@Autowired
	private BrandDAO brandDAO;
	@Autowired
	private UserDAO userDAO;

	@SuppressWarnings("serial")
	public Pager<BrandVO> getPager(BrandPageForm form) {
		Specification<Brand> specification = new Specification<Brand>() {
			@Override
			public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (form.getStatus() != null && form.getStatus() >= -2 && form.getStatus() <= 1) {
					Predicate p = criteriaBuilder.equal(root.get("status"), form.getStatus());
					predicateList.add(p);
				} else {
					Predicate p = criteriaBuilder.gt(root.get("status"), -2);
					predicateList.add(p);
				}
				if (form.getId() != null && form.getId() > 0) {
					Predicate p = criteriaBuilder.equal(root.get("id").as(String.class), form.getId());
					predicateList.add(p);
				}
				if (form.getName() != null && !"".equals(form.getName())) {
					Predicate p = criteriaBuilder.like(root.get("name"), "%" + form.getName() + "%");
					predicateList.add(p);
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
			}
		};
		Sort sort = null;
		if (form.getOrderField() != null && !"".equals(form.getOrderField())) {
			sort = Sort.by(Direction.fromString(form.getOrderDirection()), form.getOrderField());
		} else {
			sort = Sort.by(Direction.DESC, "id");
		}
		PageRequest pageRequest = PageRequest.of(form.getPageNo() - 1, form.getPageSize(), sort);
		Page<Brand> page = brandDAO.findAll(specification, pageRequest);
		if (page == null || page.getContent() == null || page.getContent().size() == 0) {
			return null;
		}
		List<BrandVO> list = new ArrayList<BrandVO>();
		for (Brand brand : page.getContent()) {
			BrandVO brandVO = new BrandVO();
			brandVO.setId(brand.getId());
			brandVO.setName(brand.getName());
			brandVO.setEnName(brand.getEnName());
			brandVO.setLogo(brand.getLogo());
			brandVO.setStatus(brand.getStatus());
			brandVO.setCreateAt(brand.getCreateAt());
			User createUser = userDAO.findById(brand.getCreateBy()).isPresent()
					? userDAO.findById(brand.getCreateBy()).get() : null;
			brandVO.setCreateUsername(createUser != null ? createUser.getUsername() : "");
			brandVO.setCensorAt(brand.getCensorAt());
			User censorUser = userDAO.findById(brand.getCensorBy()).isPresent()
					? userDAO.findById(brand.getCensorBy()).get() : null;
			brandVO.setCensorUsername(censorUser != null ? censorUser.getUsername() : "");
			list.add(brandVO);
		}
		Pager<BrandVO> pager = new Pager<BrandVO>();
		pager.setList(list);
		pager.setTotal(page.getTotalElements());
		pager.setPageCount(page.getTotalPages());
		return pager;
	}

	public Brand findByName(String name) {
		return brandDAO.findByName(name);
	}

	public Brand findById(long id) {
		return brandDAO.findById(id).get();
	}

	public void save(Brand brand) {
		brandDAO.save(brand);
	}

}
