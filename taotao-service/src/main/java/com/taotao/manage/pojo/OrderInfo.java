package com.taotao.manage.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInfo extends TbOrder implements Serializable{
	
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;

}
