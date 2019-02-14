package com.zzw.makeup.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzw.makeup.api.service.BrandService;
import com.zzw.makeup.api.service.ProductImageService;
import com.zzw.makeup.api.service.ProductService;
import com.zzw.makeup.base.entity.Brand;
import com.zzw.makeup.base.entity.Product;
import com.zzw.makeup.base.entity.ProductImage;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private BrandService brandService;

	@RequestMapping("/products")
	public String productList() throws Exception {
		JSONObject result = new JSONObject();
		List<Product> productList = productService.findAll();
		if (productList == null || productList.size() == 0) {
			throw new Exception();
		}
		result.put("statusCode", 200);
		JSONArray data = new JSONArray();
		for (Product product : productList) {
			JSONObject json = new JSONObject();
			json.put("id", product.getId());
			json.put("name", product.getName());
			Brand brand = brandService.findById(product.getBrandId());
			json.put("brandName", brand != null ? brand.getName() : "");
			List<ProductImage> prodyctImageList = productImageService.findByProductId(product.getId());
			if (prodyctImageList != null && prodyctImageList.size() > 0) {
				JSONArray images = new JSONArray();
				for (ProductImage productImage : prodyctImageList) {
					images.add(productImage.getUrl());
				}
				json.put("images", images);
			}
			data.add(json);
		}
		result.put("data", data);
		return result.toJSONString();
	}

}
