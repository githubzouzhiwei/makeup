package com.zzw.makeup.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.makeup.base.entity.ProductImage;

@Repository
public interface ProductImageDAO extends JpaRepository<ProductImage, Long> {

	@Query("select t from ProductImage t where t.productId=?1 order by sort")
	List<ProductImage> findByProductId(Long productId);

}
