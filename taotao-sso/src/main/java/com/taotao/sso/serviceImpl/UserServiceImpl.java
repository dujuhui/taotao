package com.taotao.sso.serviceImpl;


import com.taotao.common.entity.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.manage.pojo.TbUser;
import com.taotao.manage.pojo.TbUserExample;
import com.taotao.manage.service.sso.UserService;
import com.taotao.sso.mapper.TbUserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author dujuhui
 * @version 1.0
 * @date 2018/4/3
 */
@RestController
public class UserServiceImpl implements UserService {


    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private String SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        //1.判断用户名是否可用
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
            //2判断手机号是否可以使用
        } else if (type == 2) {
            criteria.andPhoneEqualTo(data);
            //3判断邮箱是否可以使用
        } else if (type == 3) {
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "参数中包含非法数据");
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list !=null && list.size() > 0) {
            //查询到数据，返回false
            return TaotaoResult.ok(false);
        }
        //数据可以使用
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult register(@RequestBody TbUser user) {
        //检查数据的有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        //判断用户名是否重复
        TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
        if (!(boolean) taotaoResult.getData()) {
            return TaotaoResult.build(400, "用户名重复");
        }
        //判断密码是否为空
        if (StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            //是否重复校验
            taotaoResult = checkData(user.getPhone(), 2);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "电话号码重复");
            }
        }
        //如果email不为空的话进行是否重复校验
        if (StringUtils.isNotBlank(user.getEmail())) {
            //是否重复校验
            taotaoResult = checkData(user.getEmail(), 3);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "email重复");
            }
        }
        //补全pojo的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码要进行md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //插入数据
        userMapper.insert(user);
        //返回注册成功
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        //判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        TbUser user = list.get(0);
        //密码要进行md5加密然后再校验
        if (!DigestUtils.md5DigestAsHex(password.getBytes())
                .equals(user.getPassword())) {
            //返回登录失败
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        //生成token，使用uuid
        String token = UUID.randomUUID().toString();
        //清空密码
        user.setPassword(null);
        //把用户信息保存到redis，key就是token，value就是用户信息
        redisTemplate.opsForValue().set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        //设置key的过期时间
        redisTemplate.expire(USER_SESSION + ":" + token, Integer.parseInt(SESSION_EXPIRE) , TimeUnit.SECONDS);
        //返回登录成功，其中要把token返回。
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json =(String)redisTemplate.opsForValue().get(USER_SESSION + ":" + token);
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "用户登录已经过期");
        }
        //重置Session的过期时间
        redisTemplate.expire(USER_SESSION + ":" + token, Integer.parseInt(SESSION_EXPIRE), TimeUnit.SECONDS );
        //把json转换成User对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
        //return TaotaoResult.ok(json);
    }


    @Override
    public TaotaoResult loginOut(String token) {
        //删除redis里的token信息
        redisTemplate.delete(USER_SESSION + ":" + token);
        return TaotaoResult.ok();
    }
}
