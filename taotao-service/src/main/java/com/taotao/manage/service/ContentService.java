package com.taotao.manage.service;

import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.pojo.TbContent;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
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
public interface ContentService {
	String REQUEST_PREFIX = "content";
	/**
	 *
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/addContent")
	TaotaoResult addContent(@RequestBody TbContent content);

	@RequestMapping(value = REQUEST_PREFIX + "/getContentByCid")
	List<TbContent> getContentByCid(@RequestParam("cid") Long cid);
}
