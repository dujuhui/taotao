package com.taotao.search.serviceImpl;

import com.taotao.common.entity.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author dujuhui
 * @version 1.0
 * @date 2018/3/31
 */
@Component
public class ActiveMq {


    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private HttpSolrServer solrServer;

    @JmsListener(destination = "itemId")
    public void onMessage(String message) {
        try {
            //从消息中取商品id
            long itemId = Long.parseLong(message);
            //根据商品id查询数据，取商品信息
            //等待事务提交
            Thread.sleep(1000);
            SearchItem searchItem = searchItemMapper.getItemById(itemId);
            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            //把文档对象写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
