package com.taotao.manage.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.entity.EasyUIDataGridResult;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.manage.mapper.TbItemDescMapper;
import com.taotao.manage.mapper.TbItemMapper;
import com.taotao.manage.pojo.TbItem;
import com.taotao.manage.pojo.TbItemDesc;
import com.taotao.manage.pojo.TbItemExample;
import com.taotao.manage.service.ItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 * @version 1.0
 */
@RestController
public class ItemServiceImpl implements ItemService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("ITEM_INFO")
	private String ITEM_INFO;

	@Value("TIEM_EXPIRE")
	private String TIEM_EXPIRE;

	@Override
	public TbItem getItemById(Long itemId) {

		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

	@Override
	public TaotaoResult addItem(@RequestBody TbItem item, String desc) {
		//生成商品id
		final long itemId = IDUtils.genItemId();
		//补全item的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据
		itemMapper.insert(item);
		//创建一个商品描述表对应的pojo
		TbItemDesc itemDesc = new TbItemDesc();
		//补全pojo的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());
		//向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		//向Activemq发送商品添加消息
		jmsTemplate.convertAndSend("itemId", itemId);
		/*jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送商品id
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});*/
		//返回结果
		return TaotaoResult.ok();
	}


	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		//查询数据库之前先查询缓存
		try {
			String json = (String) redisTemplate.opsForValue().get(ITEM_INFO + ":" + itemId  + ":DESC");
			if (StringUtils.isNotBlank(json)) {
				// 把json数据转换成pojo
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中没有查询数据库
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			//把查询结果添加到缓存
			redisTemplate.opsForValue().set(ITEM_INFO + ":" + itemId  + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置过期时间，提高缓存的利用率
			redisTemplate.expire(ITEM_INFO + ":" + itemId  + ":DESC", Long.parseLong(TIEM_EXPIRE), TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}
}
