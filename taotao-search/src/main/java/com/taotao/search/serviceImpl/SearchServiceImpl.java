package com.taotao.search.serviceImpl;

import com.taotao.common.entity.SearchResult;
import com.taotao.manage.service.search.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.dao.SearchDao;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索服务功能实现
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@RestController
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//根据查询条件拼装查询对象
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页条件
		if (page < 1) page =1;
		query.setStart((page - 1) * rows);
		if (rows < 1) rows = 10;
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算查询结果的总页数
		long recordCount = searchResult.getRecordCount();
		long pages =  recordCount / rows;
		if (recordCount % rows > 0) {
			pages++;
		}
		searchResult.setTotalPages(pages);
		//返回结果
		return searchResult;
	}

}
