package com.zzw.makeup.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzw.makeup.api.exception.ObjectNotFoundException;
import com.zzw.makeup.api.service.BrandService;
import com.zzw.makeup.base.entity.Brand;

@RestController
public class BrandController {

	private static Logger logger = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/brands", method = RequestMethod.GET)
	public String list(HttpServletRequest request) throws Exception {
		logger.info("TraceId={}, SpanId={}", request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));
		JSONObject result = new JSONObject();
		List<Brand> brandList = brandService.findAll();
		if (brandList == null || brandList.size() == 0) {
			throw new Exception();
		}
		result.put("statusCode", 200);
		JSONArray data = new JSONArray();
		for (Brand brand : brandList) {
			JSONObject json = new JSONObject();
			json.put("id", brand.getId());
			json.put("name", brand.getName());
			json.put("enName", brand.getEnName());
			json.put("logo", brand.getLogo());
			data.add(json);
		}
		result.put("data", data);
		return result.toJSONString();
	}

	@RequestMapping(value = "/brands/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable Long id) throws ObjectNotFoundException {
		JSONObject result = new JSONObject();
		Brand brand = brandService.findById(id);
		if (brand == null) {
			throw new ObjectNotFoundException(Brand.class);
		}
		result.put("statusCode", 200);
		result.put("id", brand.getId());
		result.put("name", brand.getName());
		result.put("enName", brand.getEnName());
		result.put("logo", brand.getLogo());
		return result.toJSONString();
	}

}
