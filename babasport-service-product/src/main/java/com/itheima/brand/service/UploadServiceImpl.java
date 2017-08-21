package com.itheima.brand.service;

import org.springframework.stereotype.Service;
import com.itheima.core.fastDFSUtils.FastDFSUtils;

@Service("uploadService")
public class UploadServiceImpl implements UploadService{

	@Override
	public String uploadPic(byte[] bytes, String filename, long size) throws Exception {
		return FastDFSUtils.uploadPic(bytes, filename, size);
	}

	

}
