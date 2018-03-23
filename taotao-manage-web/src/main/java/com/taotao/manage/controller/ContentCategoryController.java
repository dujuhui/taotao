package com.taotao.manage.controller;

import java.util.List;

import com.taotao.common.entity.EasyUITreeNode;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 内容分类管理Controller
 * <p>Title: ContentCategoryController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
		
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
}

