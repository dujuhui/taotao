package com.taotao.manage.controller;

import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.service.search.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexManagerController {
    @Autowired
    private SearchItemService service;

    @RequestMapping("index/import")
    public TaotaoResult importIndex(){
        TaotaoResult result = service.importItemsToIndex();
        return result;
    }
}
