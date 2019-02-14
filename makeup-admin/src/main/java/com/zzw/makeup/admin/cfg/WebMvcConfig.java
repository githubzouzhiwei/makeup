package com.zzw.makeup.admin.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 添加纯路径映射
		registry.addViewController("/admin/login.do").setViewName("admin/login");
		registry.addViewController("/admin/index.do").setViewName("admin/index");
	}

}
