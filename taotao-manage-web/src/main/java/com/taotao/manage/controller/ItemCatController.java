package com.taotao.manage.controller;

import com.taotao.common.entity.EasyUITreeNode;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理Controller
 * @author dujuhui
 * @version 1.0
 * @date 2018/3/20
 */

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService service;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue="0")Long parentId){
        List<EasyUITreeNode> list = service.getTreeNode(parentId);
        return list;
    }
}
