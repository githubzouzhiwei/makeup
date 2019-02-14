package com.zzw.makeup.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zzw.makeup.admin.entity.User;
import com.zzw.makeup.admin.form.product.ProductEditForm;
import com.zzw.makeup.admin.form.product.ProductPageForm;
import com.zzw.makeup.admin.service.BrandService;
import com.zzw.makeup.admin.service.ProductImageService;
import com.zzw.makeup.admin.service.ProductService;
import com.zzw.makeup.admin.vo.Pager;
import com.zzw.makeup.admin.vo.ProductVO;
import com.zzw.makeup.base.entity.Brand;
import com.zzw.makeup.base.entity.Product;
import com.zzw.makeup.base.entity.ProductImage;

@Controller
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	private static final String navTabId = "list-product";
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductImageService productImageService;

	@RequestMapping("/list.do")
	public String list(ProductPageForm form, HttpServletRequest request) {
		Pager<ProductVO> pager = productService.getPager(form);
		request.setAttribute("pager", pager);
		request.setAttribute("form", form);
		return "admin/product/list";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String toCreate() {
		return "admin/product/edit";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	public void create(ProductEditForm form, @RequestParam(name = "brand.id", required = true) Long brandId,
			@RequestParam(name = "images", required = true) String[] images, HttpServletRequest request,
			HttpServletResponse response) {
		if (brandId <= 0 || images == null || images.length == 0) {
			return;
		}
		// 判断产品名是否存在
		String name = form.getName();
		Product tmp = productService.findByName(name);
		if (tmp != null) {
			callback(response, 300, "产品已存在", null, null);
			return;
		}
		Product product = new Product();
		product.setBrandId(brandId);
		product.setName(name);
		product.setStatus(0);
		product.setCreateAt(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		product.setCreateBy(user.getId());
		productService.save(product, images);
		callback(response, 200, "添加成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response) {
		String strId = request.getParameter("id");
		long id = Long.parseLong(strId);
		if (id <= 0) {
			return null;
		}
		Product product = productService.findById(id);
		if (product == null) {
			return null;
		}
		Brand brand = brandService.findById(product.getBrandId());
		List<ProductImage> prodcutImageList = productImageService.findByProductId(id);
		request.setAttribute("entity", product);
		request.setAttribute("brand", brand);
		request.setAttribute("prodcutImageList", prodcutImageList);
		return "admin/product/edit";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update(ProductEditForm form, @RequestParam(name = "brand.id", required = true) Long brandId,
			@RequestParam(name = "images", required = true) String[] images, HttpServletRequest request,
			HttpServletResponse response) {
		long id = form.getId();
		if (id <= 0 || brandId <= 0 || images == null || images.length == 0) {
			return;
		}
		Product product = productService.findById(id);
		if (product == null) {
			return;
		}
		// 判断产品名是否存在
		String name = form.getName();
		Product tmp = productService.findByName(name);
		if (tmp != null && tmp.getId() != id) {
			callback(response, 300, "产品名已存在", null, null);
			return;
		}
		product.setName(name);
		product.setUpdateAt(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		product.setUpdateBy(user.getId());
		productService.save(product, images);
		callback(response, 200, "修改成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/status.do", method = RequestMethod.POST)
	public void status(@RequestParam(value = "ids", required = true) String[] ids,
			@Range(min = -2, max = 1) @RequestParam(value = "status", required = true) Integer status,
			HttpServletRequest request, HttpServletResponse response) {
		if (ids == null || ids.length == 0) {
			return;
		}
		Date now = new Date();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for (int i = 0; i < ids.length; ++i) {
			long id = Long.parseLong(ids[i]);
			if (id <= 0) {
				continue;
			}
			Product entity = productService.findById(id);
			if (entity == null || entity.getStatus() == status) {
				continue;
			}
			entity.setStatus(status);
			entity.setUpdateAt(now);
			entity.setUpdateBy(user.getId());
			if (status == -1 || status == 1) {
				entity.setCensorAt(now);
				entity.setCensorBy(user.getId());
			}
			productService.save(entity, null);
		}
		callback(response, 200, "更新成功", navTabId, null);
	}

}
