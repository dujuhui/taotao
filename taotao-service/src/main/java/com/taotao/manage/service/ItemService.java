package com.taotao.manage.service;


import com.taotao.common.entity.EasyUIDataGridResult;
import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.pojo.TbItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = Global.TAOTAO_MANAGER)
public interface ItemService {

	String REQUEST_PREFIX = "item";
	/**
	 *
	 */
	@GetMapping(value = REQUEST_PREFIX + "/getItemById")
	TbItem getItemById(@RequestParam("itemId") Long itemId);

	/**
	 * 获取商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping(value = REQUEST_PREFIX + "/getItemList")
	EasyUIDataGridResult getItemList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows);

	/**
	 * 添加商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/addItem")
	TaotaoResult addItem(@RequestBody TbItem item, @RequestParam("desc") String desc);
}
