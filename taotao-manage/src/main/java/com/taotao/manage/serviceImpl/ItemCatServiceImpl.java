package com.taotao.manage.serviceImpl;

import com.taotao.common.entity.EasyUITreeNode;
import com.taotao.manage.mapper.TbItemCatMapper;
import com.taotao.manage.pojo.TbItemCat;
import com.taotao.manage.pojo.TbItemCatExample;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujuhui
 * @version 1.0
 * @date 2018/3/20
 */
@RestController
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getTreeNode(Long parentId) {
//根据父节点id查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //设置parentid
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            //如果节点下有子节点“closed”，如果没有子节点“open”
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到节点列表
            resultList.add(node);
        }
        return resultList;
    }
}
