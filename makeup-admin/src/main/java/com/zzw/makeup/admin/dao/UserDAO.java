package com.zzw.makeup.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.makeup.admin.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> , JpaSpecificationExecutor<User>{

	@Query("select t from User t where t.username=?1")
	User findByUsername(String username);

}
