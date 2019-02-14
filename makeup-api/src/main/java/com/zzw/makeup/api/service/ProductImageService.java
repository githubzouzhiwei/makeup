package com.zzw.makeup.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzw.makeup.base.dao.ProductImageDAO;
import com.zzw.makeup.base.entity.ProductImage;

@Service
public class ProductImageService {

	@Autowired
	private ProductImageDAO productImageDAO;

	public List<ProductImage> findByProductId(long productId) {
		return productImageDAO.findByProductId(productId);
	}

}
