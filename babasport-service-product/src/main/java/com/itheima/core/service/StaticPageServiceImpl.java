package com.itheima.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{

	
	private Configuration configuration;
	
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}
	public String getRealPath(String path){
		return servletContext.getRealPath(path);
	}
	
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.configuration = freeMarkerConfigurer.getConfiguration();
	}


	@Override
	public void index(Map root,String id){
		String path = getRealPath("/html/product/"+id+".html");
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdir();
		}
		Writer out=null;
		try {
			Template tem = configuration.getTemplate("product.html");
			out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			tem.process(root, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
