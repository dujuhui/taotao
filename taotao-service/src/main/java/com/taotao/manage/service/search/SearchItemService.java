package com.taotao.manage.service.search;


import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author  dujuhui
 * @date    2018/3/20
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_SEARCH)
public interface SearchItemService {
	String REQUEST_PREFIX = "search";
	/**
	 *
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/importItemsToIndex")
	TaotaoResult importItemsToIndex();
}
