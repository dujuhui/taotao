package com.taotao.manage.service.order;


import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.pojo.OrderInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author  dujuhui
 * @date    2018/3/20
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_ORDER)
public interface OrderService {
	String REQUEST_PREFIX = "order";

	/**
	 * 创建订单
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/createOrder")
	TaotaoResult createOrder(OrderInfo orderInfo);
}
