package com.itheima.core.fastDFSUtils;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FastDFSUtils {

	public static String uploadPic(byte[] pic,String name,Long size) throws Exception{
		//设置全局的tracker的IP
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf"); 
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		//用tarkerClientAPI连接tarcker
		TrackerClient tarckerClient = new TrackerClient();
		//获取storage的地址
		TrackerServer trackerServer = tarckerClient.getConnection();
		StorageServer storageServer = null;
		
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
		String ext = FilenameUtils.getExtension(name);
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("filename", name);
		meta_list[1] = new NameValuePair("fileext", ext);
		meta_list[2] = new NameValuePair("filesize", String.valueOf(size));
		String path = storageClient1.upload_file1(pic, ext, meta_list);
		return path;
	}
}
