package com.itheima.brand.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadPicController {

	@RequestMapping(value="/upload/uploadBranPic.do")
	public void uploadBrandPic(MultipartFile pic,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		//获取文件的后缀名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		//拼接文件的路径
		String path = "/uploadBrand/"+UUID.randomUUID().toString()+"."+ext;
		//获取文件的真实全路径
		String url = request.getSession().getServletContext().getRealPath(path);
		System.out.println(url);
		//将文件写入到磁盘
		pic.transferTo(new File(url));
		//要将图片在项目中的路径返回到页面 进行图片回显  格式是json
		JSONObject jo = new JSONObject();
		jo.put("path", path);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
		
	}
}
