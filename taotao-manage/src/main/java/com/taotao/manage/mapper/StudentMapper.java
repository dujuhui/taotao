package com.taotao.manage.mapper;

import com.taotao.manage.pojo.Student;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author  dujuhui
 * @date    2018/3/14
 * @version 1.0
 */

public interface StudentMapper {

     Student getById(@Param("id") String id);
}
