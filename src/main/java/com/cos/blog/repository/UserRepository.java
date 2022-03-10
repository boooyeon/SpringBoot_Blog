package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean등록이 된다
@ Repository // 생략 가능
// JpaRepository는 User테이블이 관리하는 레포지토리, User 테이블의 PK는 Integer다
public interface UserRepository extends JpaRepository<User, Integer>{
	
}

	//1. JPA Naming 쿼리 전략
// findByUsernameAndPassword => SELECT * FROM user WHERE username=첫번째 파라미터 AND password=두번째 파라미터;
// User findByUsernameAndPassword(String username, String password);

	
	// 2. 다른 방법
//	@Query(value="SELECT * FROM user WHERE username=첫번째 파라미터 AND password=두번째파라미터", nativeQuery=true)
//	User login(String username, String password);