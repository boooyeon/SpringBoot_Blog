package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// RestController: html 파일이 아니라 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {
   
   @Autowired // 의존성 주입(DI)
   private UserRepository userRepository;
   
   
   // save 함수는 id를 전달하지 않으면 insert를 해주고
   // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
   // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 함
   // email, password만 변경
   // http://localhost:8000/blog/dummy/user/3 (update)
   
   //Delete(삭제하기)
   @DeleteMapping("/dummy/user/{id}")
   public String delete(@PathVariable int id) {
	   try {
		   userRepository.deleteById(id);
	} catch (EmptyResultDataAccessException e) {
		return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다";
	}
	   return "삭제되었습니다.id: " + id;
   }
   
   // Update(수정하기)
   @Transactional
   @PutMapping("/dummy/user/{id}")
   public User updateUser(@PathVariable int id, @RequestBody User requestUser ) { //json 데이터를 요청 => Java Object(MessageConverter가 Jackson라이브러리)로 변환해서 받아줌
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());  
		
		// 방법1
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(requestUser);
		
		// 방법2.@Transactional
		// 더티 체킹 -> save를 사용하지 않아도 데이터 변경이 가능
		return user;
   }
   
   // Select(전체 찾기)
   // http://localhost:8000/blog/dummy/users
   @GetMapping("/dummy/users")
   public List<User> list() {
	   return userRepository.findAll();
   }
   
   // Select(page로 찾기)
   // 한페이지당 2건의 데이터를 리턴받아 볼 예정
   // http://localhost:8000/blog/dummy/user/
   @GetMapping("dummy/user")
   public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
	   Page<User> pagingUser = userRepository.findAll(pageable);
	   
	   // 이런식으로 분기처리도 가능
//	   if(pagingUser.isLast()) {
//		   
//	   }
	   List<User> users = pagingUser.getContent();
	   return users;
   }
   
   // Select(id로 찾기)
   // {id} 주소로 파라미터를 전달 받을 수 있음
   // http://localhost:8000/blog/dummy/user/3
   @GetMapping("/dummy/user/{id}")
   public User detail(@PathVariable int id) {
	   // user /4을 찾는데 DB에서 못찾아오게되면 user가 return null이 되기 때문에 프로그램에 문제가 발생
	   // Optional로 너의 User 객체를 감싸서 가져와 null인지 아닌지 판단해서 return
	   
	   //방법1.람다식 	
//	   	User user = userRepository.findById(id).orElseThrow(()->{
//	   		return new IllegalArgumentException("해당 사용자는 없습니다.");
//	   	});
	   
	   User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		   @Override
		   public IllegalArgumentException get() {
			   // TODO Auto-generated method stub
			   return new IllegalArgumentException("해당 유저는 없습니다. id: " +id);
		   }
	   	});
	   // user 객체 => 자바 오브젝트
	   // 요청: 웹브라우저 => html 파일을 인식
	   // 따라서, 변환(웹브라우저가 이해할 수 있는 데이터로 변환) => json(Gson 라이브러리)
	   // 스프링부트 = MessageConverter가 응답시에 자동 작동
	   // 즉, 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서 user 오브젝트를 json으로 변환해서 브라우저에게 던짐
	   	return user; 
   }
   
   // Insert(입력하기)
   //http://localhost:8000/blog/dummy/join(요청)
   //http의 body에 username, password, email 데이터를 가지고(요청)
   @PostMapping("/dummy/join")
   public String join(@RequestBody User requestUser) { //key=value(약속된 규칙)
      System.out.println("id : "+ requestUser.getId());
      System.out.println("username : "+requestUser.getUsername());
      System.out.println("password : "+requestUser.getPassword());
      System.out.println("email : "+requestUser.getEmail());
      System.out.println("role : "+requestUser.getRole());
      System.out.println("createDate : "+requestUser.getCreateDate());
      
      
      requestUser.setRole(RoleType.USER);
      userRepository.save(requestUser);
      return "회원가입이 완료되었습니다.";
   }

}