package com.taotao.manage.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.entity.EasyUIDataGridResult;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.manage.mapper.TbItemDescMapper;
import com.taotao.manage.mapper.TbItemMapper;
import com.taotao.manage.pojo.TbItem;
import com.taotao.manage.pojo.TbItemDesc;
import com.taotao.manage.pojo.TbItemExample;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


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

}
