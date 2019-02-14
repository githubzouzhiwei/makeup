package com.zzw.makeup.admin.controller;

import java.util.Date;

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
import com.zzw.makeup.admin.form.brand.BrandEditForm;
import com.zzw.makeup.admin.form.brand.BrandPageForm;
import com.zzw.makeup.admin.service.BrandService;
import com.zzw.makeup.admin.vo.BrandVO;
import com.zzw.makeup.admin.vo.Pager;
import com.zzw.makeup.base.entity.Brand;

@Controller
@RequestMapping("/admin/brand")
public class BrandController extends BaseController {

	private static final String navTabId = "list-brand";
	@Autowired
	private BrandService brandService;

	@RequestMapping("/list.do")
	public String list(BrandPageForm form, HttpServletRequest request) {
		Pager<BrandVO> pager = brandService.getPager(form);
		request.setAttribute("pager", pager);
		request.setAttribute("form", form);
		return "admin/brand/list";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String toCreate() {
		return "admin/brand/edit";
	}

	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	public void create(BrandEditForm form, HttpServletRequest request, HttpServletResponse response) {
		// 判断品牌名是否存在
		String name = form.getName();
		Brand brand = brandService.findByName(name);
		if (brand != null) {
			callback(response, 300, "品牌已存在", null, null);
			return;
		}
		brand = new Brand();
		brand.setName(name);
		brand.setEnName(form.getEnName());
		brand.setLogo(form.getLogo());
		brand.setCreateAt(new Date());
		brand.setStatus(0);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		brand.setCreateBy(user.getId());
		brandService.save(brand);
		callback(response, 200, "添加成功", navTabId, "closeCurrent");
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response) {
		String strId = request.getParameter("id");
		long id = Long.parseLong(strId);
		if (id <= 0) {
			return null;
		}
		Brand brand = brandService.findById(id);
		if (brand == null) {
			return null;
		}
		request.setAttribute("entity", brand);
		return "admin/brand/edit";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update(BrandEditForm form, HttpServletRequest request, HttpServletResponse response) {
		long id = form.getId();
		if (id <= 0) {
			return;
		}
		Brand brand = brandService.findById(id);
		if (brand == null) {
			return;
		}
		// 判断品牌名是否存在
		String name = form.getName();
		Brand tmp = brandService.findByName(name);
		if (tmp != null && tmp.getId() != id) {
			callback(response, 300, "品牌名已存在", null, null);
			return;
		}
		brand.setName(name);
		brand.setEnName(form.getEnName());
		brand.setLogo(form.getLogo());
		brand.setUpdateAt(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		brand.setUpdateBy(user.getId());
		brandService.save(brand);
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
			Brand entity = brandService.findById(id);
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
			brandService.save(entity);
		}
		callback(response, 200, "更新成功", navTabId, null);
	}

	/**
	 * 查找返回
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@RequestMapping("/select.do")
	public String select(BrandPageForm form, HttpServletRequest request) {
		Pager<BrandVO> pager = brandService.getPager(form);
		request.setAttribute("pager", pager);
		request.setAttribute("form", form);
		return "admin/brand/select";
	}

}
