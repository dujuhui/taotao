package com.taotao.manage.service;


import com.taotao.common.entity.EasyUIDataGridResult;
import com.taotao.common.entity.Global;
import com.taotao.manage.pojo.TbItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = Global.TAOTAO_MANAGER)
public interface ItemService {

	String REQUEST_PREFIX = "item";
	/**
	 *
	 */
	@GetMapping(value = REQUEST_PREFIX + "/getItemById")
	TbItem getItemById(@RequestParam("itemId") Long itemId);

	@GetMapping(value = REQUEST_PREFIX + "/getItemList")
	EasyUIDataGridResult getItemList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows);
}
