package com.zzw.makeup.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.makeup.admin.entity.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {

	@Query("select t from Role t where t.name=?1")
	Role findByName(String name);

}
