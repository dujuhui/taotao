package com.taotao.manage.service;

import com.taotao.common.entity.EasyUITreeNode;
import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 *
 * @author  dujuhui
 * @date    2018/3/20
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_CONTENT)
public interface ContentCategoryService {

	String REQUEST_PREFIX = "content";
	/**
	 *
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/getContentCategoryList")
	List<EasyUITreeNode> getContentCategoryList(@RequestParam("parentId") Long parentId);


	@RequestMapping(value = REQUEST_PREFIX + "/addContentCategory")
	TaotaoResult addContentCategory(@RequestParam("parentId") Long parentId, @RequestParam("name") String name);
}
