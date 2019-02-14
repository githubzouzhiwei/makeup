package com.zzw.makeup.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzw.makeup.base.dao.BrandDAO;
import com.zzw.makeup.base.entity.Brand;

@Service
public class BrandService {

	@Autowired
	private BrandDAO brandDAO;

	public List<Brand> findAll() {
		return brandDAO.findAll();
	}

	public Brand findById(long id) {
		return brandDAO.findById(id).orElse(null);
	}

}
