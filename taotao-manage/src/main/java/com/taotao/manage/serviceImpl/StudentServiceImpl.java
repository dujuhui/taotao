package com.taotao.manage.serviceImpl;

import com.taotao.manage.mapper.StudentMapper;
import com.taotao.manage.pojo.Student;
import com.taotao.manage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dujuhui
 *
 * @version 1.0
 * @date 2018/3/14
 */
@RestController
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper mapper;

    @Override
    public Student getById(String id) {

        return mapper.getById(id);
    }
}
