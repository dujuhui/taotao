package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.taotao.manage.pojo.TbItem;
import com.taotao.manage.pojo.TbItemDesc;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ItemAddMesssageListener{

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@JmsListener(destination = "itemId")
	public void onMessage(String message) {
		try {
			//从消息中取商品id
			Long itemId = Long.parseLong(message);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品id查询商品信息及商品描述
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			//使用freemarker生成静态页面
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			//1.创建模板
			//2.加载模板对象
			Template template = configuration.getTemplate("item.ftl");
			//3.准备模板需要的数据
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			//4.指定输出的目录及文件名
			Writer out = new FileWriter(new File(HTML_OUT_PATH + itemId + ".html"));
			//5.生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
