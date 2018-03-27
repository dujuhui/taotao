package com.taotao.search.bean;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MySolrServer {

    @Bean
    public HttpSolrServer getSolrServer(){
        HttpSolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr");
        return server;
    }
}
