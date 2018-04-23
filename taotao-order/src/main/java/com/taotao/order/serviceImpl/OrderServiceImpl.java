package com.taotao.order.serviceImpl;


import java.util.Date;
import java.util.List;

import com.taotao.common.entity.TaotaoResult;
import com.taotao.common.utils.RedisUtil;
import com.taotao.manage.pojo.OrderInfo;
import com.taotao.manage.pojo.TbOrderItem;
import com.taotao.manage.pojo.TbOrderShipping;
import com.taotao.manage.service.order.OrderService;
import com.taotao.order.mapper.TbOrderItemMapper;
import com.taotao.order.mapper.TbOrderMapper;
import com.taotao.order.mapper.TbOrderShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**
 * 订单处理Server
 * <p>Title: OrderServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	@Autowired
	private RedisUtil redisUtil;
//	@Autowired
//	private RedisTemplate redisTemplate;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//生成订单号,可以使用redis的incr生成
		if (!redisUtil.hasKey(ORDER_ID_GEN_KEY)) {
			//设置初始值
			redisUtil.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		String orderId = redisUtil.incr(ORDER_ID_GEN_KEY,1) + "";
		//向订单表插入数据，需要补全pojo的属性
		orderInfo.setOrderId(orderId);
		//免邮费
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		//订单创建时间
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//向订单 表插入数据
		orderMapper.insert(orderInfo);
		//向订单明细表插入数据。
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//获得明细主键
			String oid = redisUtil.incr(ORDER_ITEM_ID_GEN_KEY,1) + "";
			tbOrderItem.setId(oid);
			tbOrderItem.setOrderId(orderId);
			//插入明细数据
			orderItemMapper.insert(tbOrderItem);
		}
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回订单号
		return TaotaoResult.ok(orderId);
	}

}
