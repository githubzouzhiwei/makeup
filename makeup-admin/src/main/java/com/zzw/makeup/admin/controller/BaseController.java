package com.zzw.makeup.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class BaseController {
	
	protected void callback(HttpServletResponse resp, int statusCode, String message, String navTabId, String callbackType) {
		JSONObject json = new JSONObject();
		resp.setCharacterEncoding("UTF-8");
		if (statusCode > 0) {
			json.put("statusCode", statusCode);
		}
		if (!StringUtils.isEmpty(message)) {
			json.put("message", message);
		}
		if (!StringUtils.isEmpty(navTabId)) {
			json.put("navTabId", navTabId);
		}
		if (!StringUtils.isEmpty(callbackType)) {
			json.put("callbackType", callbackType);
		}
		try {
			resp.getWriter().println(json.toJSONString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
