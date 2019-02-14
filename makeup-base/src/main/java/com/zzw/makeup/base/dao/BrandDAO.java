package com.zzw.makeup.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.makeup.base.entity.Brand;

@Repository
public interface BrandDAO extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
	
	@Query("select t from Brand t where t.name=?1")
	Brand findByName(String name);
	
	@Query("select t from Brand t where t.status=1")
	List<Brand> findAll();
	
}
