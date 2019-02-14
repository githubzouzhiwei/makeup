package com.zzw.makeup.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.makeup.base.entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query("select t from Product t where t.name=?1")
	Product findByName(String name);
	@Query("select t from Product t where t.status=1")
	List<Product> findAll();

}
