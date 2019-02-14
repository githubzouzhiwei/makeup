package com.zzw.makeup.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzw.makeup.base.dao.ProductDAO;
import com.zzw.makeup.base.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDAO productDAO;

	public List<Product> findAll() {
		return productDAO.findAll();
	}

}
