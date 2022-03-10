package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 함(IoC를 해줌)
// Service의 의미: 
// Service가 필요한 이유 1: 트랜잭션 관리
@Service

public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired // DI가 되서 주입이 됨
	private BCryptPasswordEncoder encoder;

	
	@Transactional
	public void 회원가입(User user){
		String rawPassword = user.getPassword(); // 원 비밀번호
		String encPassword = encoder.encode(rawPassword); // 해쉬 비밀번호
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

//	@Transactional(readOnly=true) // Select 할 때 트렌젝션 시 서비스 종료시에 트랜잭션 종료(정합성)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()); //여러번하더라도 같은 데이터가 찾아짐
//	}
}
