package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

// 아래 3개는 시큐리티 설정하는데 세트라고생각하면 
@Configuration //빈 등록(IoC 관리)
@EnableWebSecurity //시큐리티 필터가 등록이 됨 => 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다는 의미
@EnableGlobalMethodSecurity(prePostEnabled=true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 의미
public class SequrityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean // IoC가 됨 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화(테스트 시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
		}
}
