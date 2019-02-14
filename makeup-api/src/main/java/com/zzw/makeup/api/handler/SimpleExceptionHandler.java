package com.zzw.makeup.api.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONObject;
import com.zzw.makeup.api.exception.AccessForbiddenException;
import com.zzw.makeup.api.exception.ObjectNotFoundException;

@ControllerAdvice(annotations = { Controller.class })
public class SimpleExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(SimpleExceptionHandler.class);

	// 通用异常的处理，返回500
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String simpleExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error(request.getRequestURL().toString());
		logger.error(e.getMessage(), e);
		JSONObject json = new JSONObject();
		json.put("error", "server exception!");
		return json.toJSONString();
	}

	// 空结果集
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { ObjectNotFoundException.class })
	public String notFoundExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error(request.getRequestURL().toString());
		logger.error(e.getMessage(), e);
		JSONObject json = new JSONObject();
		json.put("error", "Object not found!");
		return json.toJSONString();
	}

	// 禁止访问
	@ResponseBody
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(value = { AccessForbiddenException.class })
	public String forbiddenExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error(request.getRequestURL().toString());
		logger.error(e.getMessage(), e);
		JSONObject json = new JSONObject();
		json.put("error", "access forbidden!");
		return json.toJSONString();
	}

}
