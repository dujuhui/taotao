package com.taotao.manage.controller;

import java.util.HashMap;
import java.util.Map;

import com.taotao.manage.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;

/**
 * 图片上传controller
 * <p>Title: PictureController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		try {
			//接收上传的文件
			//取扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url = IMAGE_SERVER_URL + url;
			//响应上传图片的url
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
		
	}
}
