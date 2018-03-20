package com.taotao.manage.service;

import com.taotao.common.entity.EasyUITreeNode;
import com.taotao.common.entity.Global;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 
 * @author  dujuhui
 * @date    2018/3/20
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_MANAGER)
public interface ItemCatService {
    String REQUEST_PREFIX = "itemCat";
    /**
     *
     */
    @RequestMapping(value = REQUEST_PREFIX + "/getTreeNode")
     List<EasyUITreeNode> getTreeNode(@RequestParam("parentId")Long parentId);
}
