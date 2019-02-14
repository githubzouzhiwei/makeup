package com.zzw.makeup;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

import com.zzw.makeup.base.cfg.DbConfig;

@SpringCloudApplication
@ImportAutoConfiguration(value = DbConfig.class)
public class Application {

	public static void main(String[] args) {
		// 设置时区
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
		SpringApplication.run(Application.class, args);
	}

}
