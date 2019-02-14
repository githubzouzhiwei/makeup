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
import com.zzw.makeup.admin.form.product.ProductPageForm;
import com.zzw.makeup.admin.vo.Pager;
import com.zzw.makeup.admin.vo.ProductVO;
import com.zzw.makeup.base.dao.BrandDAO;
import com.zzw.makeup.base.dao.ProductDAO;
import com.zzw.makeup.base.dao.ProductImageDAO;
import com.zzw.makeup.base.entity.Brand;
import com.zzw.makeup.base.entity.Product;
import com.zzw.makeup.base.entity.ProductImage;

@Service
public class ProductService {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductImageDAO productImageDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BrandDAO brandDAO;

	@SuppressWarnings("serial")
	public Pager<ProductVO> getPager(ProductPageForm form) {
		Specification<Product> specification = new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery,
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
		Page<Product> page = productDAO.findAll(specification, pageRequest);
		if (page == null || page.getContent() == null || page.getContent().size() == 0) {
			return null;
		}
		List<ProductVO> list = new ArrayList<ProductVO>();
		for (Product product : page.getContent()) {
			ProductVO productVO = new ProductVO();
			productVO.setId(product.getId());
			productVO.setName(product.getName());
			Brand brand = brandDAO.findById(product.getBrandId()).isPresent()
					? brandDAO.findById(product.getBrandId()).get() : null;
			productVO.setBrandName(brand != null ? brand.getName() : "");
			List<ProductImage> productImagelist = productImageDAO.findByProductId(product.getId());
			List<String> images = new ArrayList<String>();
			if (productImagelist != null && productImagelist.size() > 0) {
				for (ProductImage pi : productImagelist) {
					images.add(pi.getUrl());
				}
			}
			productVO.setImages(images.toArray(new String[images.size()]));
			productVO.setStatus(product.getStatus());
			productVO.setCreateAt(product.getCreateAt());
			User createUser = userDAO.findById(product.getCreateBy()).isPresent()
					? userDAO.findById(product.getCreateBy()).get() : null;
			productVO.setCreateUsername(createUser != null ? createUser.getUsername() : "");
			productVO.setCensorAt(product.getCensorAt());
			User censorUser = userDAO.findById(product.getCensorBy()).isPresent()
					? userDAO.findById(product.getCensorBy()).get() : null;
			productVO.setCensorUsername(censorUser != null ? censorUser.getUsername() : "");
			list.add(productVO);
		}
		Pager<ProductVO> pager = new Pager<ProductVO>();
		pager.setList(list);
		pager.setTotal(page.getTotalElements());
		pager.setPageCount(page.getTotalPages());
		return pager;
	}

	public Product findById(long id) {
		return productDAO.findById(id).get();
	}

	public Product findByName(String name) {
		return productDAO.findByName(name);
	}

	public void save(Product product, String[] images) {
		if (product == null) {
			return;
		}
		productDAO.save(product);
		if (images == null || images.length == 0) {
			return;
		}
		// 保存关联图片
		List<ProductImage> saveList = new ArrayList<ProductImage>();
		for (int i = 0; i < images.length; ++i) {
			String image = images[i];
			ProductImage entity = new ProductImage();
			entity.setProductId(product.getId());
			entity.setUrl(image);
			entity.setSort(i + 1);
			saveList.add(entity);
		}
		// 若是更新，则判断图片是否已存在
		if (product.getId() > 0) {
			List<ProductImage> hadList = productImageDAO.findByProductId(product.getId());
			for (ProductImage had : hadList) {
				boolean delete = true;
				for (ProductImage save : saveList) {
					// 存在则删除状态设为false
					if (had.getUrl().equals(save.getUrl())) {
						delete = false;
						save.setId(had.getId());
						break;
					}
				}
				if (delete) {
					productImageDAO.delete(had);
				}
			}
		}
		for (ProductImage save : saveList) {
			productImageDAO.save(save);
		}
	}

}
