package com.taotao.manage.controller;

import com.taotao.manage.pojo.Student;
import com.taotao.manage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dujuhui
 * @version 1.0
 * @date 2018/3/15
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService service;

    @RequestMapping("/{id}")
    @ResponseBody
    public Student getById(@PathVariable("id") String id){
        return service.getById(id);
    }

    @RequestMapping("/no/{id}")
    public String name(ModelMap map, @PathVariable("id") String id) {
        map.addAttribute("student",service.getById(id));
        return "hello";
    }

}
