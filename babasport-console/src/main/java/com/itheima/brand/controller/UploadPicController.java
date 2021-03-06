package com.itheima.brand.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.itheima.brand.service.UploadService;
import com.itheima.core.web.Constants;

@Controller
public class UploadPicController {

	@Autowired
	private UploadService uploadService;
	@RequestMapping(value="/upload/uploadBrandPic.do")
	public void uploadBrandPic(MultipartFile pic,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		/*//获取文件的后缀名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		//拼接文件的路径
		String path = "/uploadBrand/"+UUID.randomUUID().toString()+"."+ext;
		//获取文件的真实全路径
		String url = request.getSession().getServletContext().getRealPath(path);
		System.out.println(url);
		//将文件写入到磁盘
		pic.transferTo(new File(url));*/
		String filename = pic.getOriginalFilename();
		byte[] bytes = pic.getBytes();
		long size = pic.getSize();
		String path = uploadService.uploadPic(bytes,filename,size);
		//要将图片在项目中的路径返回到页面 进行图片回显  格式是json
		JSONObject jo = new JSONObject();
		
		jo.put("path",Constants.IMG_URl + path);	
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
		
	}
	@RequestMapping(value="/upload/uploadPics.do")
	public @ResponseBody List<String>  uploadPics(@RequestParam(required=false) MultipartFile[] pics) throws Exception{
		List<String> lists = new ArrayList<>();
		for (MultipartFile pic : pics) {
			String name = pic.getOriginalFilename();
			long size = pic.getSize();
			String path = uploadService.uploadPic(pic.getBytes(), name, size);
			lists.add(Constants.IMG_URl+path);
		}
		return lists;
	}
	@RequestMapping(value="/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MultipartRequest mr =(MultipartRequest)request;
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			JSONObject jo = new JSONObject();
			jo.put("url", Constants.IMG_URl+path);
			jo.put("error", 0);
			response.setContentType("application/json;utf-8");
			response.getWriter().write(jo.toString());
		}
	}
}
