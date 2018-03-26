package com.taotao.content.serviceImpl;

import java.util.Date;
import java.util.List;

import com.taotao.common.entity.TaotaoResult;
import com.taotao.content.mapper.TbContentMapper;
import com.taotao.manage.pojo.TbContent;
import com.taotao.manage.pojo.TbContentExample;
import com.taotao.manage.service.ContentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	/*@Autowired
	private JedisClient jedisClient;*/

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	@Override
	public TaotaoResult addContent(@RequestBody TbContent content) {
		//补全pojo的属性
		content.setCreated( new Date());
		content.setUpdated(new Date());
		//插入到内容表
		contentMapper.insert(content);
		//同步缓存
		//删除对应的缓存信息
		redisTemplate.opsForHash().delete(INDEX_CONTENT, content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentByCid(Long cid) {
		//先查询缓存
		//添加缓存不能影响正常业务逻辑
		try {
			//查询缓存
			String json = (String)redisTemplate.opsForHash().get(INDEX_CONTENT, cid + "");
			//查询到结果，把json转换成List返回
			if (StringUtils.isNotBlank(json) && !json.equals("null")) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中没有命中，需要查询数据库
		TbContentExample example = new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//把结果添加到缓存
		try {
			redisTemplate.opsForHash().put(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		return list;
	}

}
