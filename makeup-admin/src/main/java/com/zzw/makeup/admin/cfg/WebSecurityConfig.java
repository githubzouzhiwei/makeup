package com.zzw.makeup.admin.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zzw.makeup.admin.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Override
	protected UserDetailsService userDetailsService() {
		return userService;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setHideUserNotFoundExceptions(false);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.csrf().disable();// 关闭csrf，csrf不支持POST
		http.authorizeRequests()// 开始请求权限配置
				.antMatchers("/**/*.png").permitAll()
				.antMatchers("/admin/**").authenticated().anyRequest().permitAll().and().formLogin()
				.loginPage("/admin/login.do").defaultSuccessUrl("/admin/index.do", true).permitAll().and().rememberMe()
				.tokenValiditySeconds(10 * 60).and().logout().logoutUrl("/admin/logout.do")
				.logoutSuccessUrl("/admin/login.do").permitAll();
	}
	
}
