package com.taotao.manage.service;



import com.taotao.common.entity.Global;
import com.taotao.manage.pojo.Student;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author  dujuhui
 * @date    2018/3/14
 * @version 1.0
 */
@FeignClient(value = Global.TAOTAO_MANAGER)
public interface StudentService {
     String REQUEST_PREFIX = "student";
     /**
      * 判断商品是否是自己的
      */
     @GetMapping(value = REQUEST_PREFIX + "/getById")
     Student getById(@RequestParam("id") String id);
}
