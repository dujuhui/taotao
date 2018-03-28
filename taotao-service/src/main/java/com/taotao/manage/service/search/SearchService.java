package com.taotao.manage.service.search;


import com.taotao.common.entity.Global;
import com.taotao.common.entity.SearchResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author  dujuhui
 * @date    2018/3/20
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_SEARCH)
public interface SearchService {
	String REQUEST_PREFIX = "search";
	/**
	 *
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/search")
	SearchResult search(@RequestParam("queryString") String queryString,@RequestParam("page") int page,@RequestParam("rows") int rows) throws Exception;
}
