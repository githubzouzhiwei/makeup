package com.zzw.makeup.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/common")
public class CommonController {

	/**
	 * 上传图片
	 * 
	 * @return
	 */
	@RequestMapping("/uploadPic.do")
	public String toUploadPic(HttpServletRequest request) {
		request.setAttribute("callback", request.getParameter("callback"));
		return "admin/common/uploadPic";
	}

}
