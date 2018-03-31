package com.taotao.manage.service.sso;


import com.taotao.common.entity.Global;
import com.taotao.common.entity.TaotaoResult;
import com.taotao.manage.pojo.TbUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = Global.TAOTAO_SSO)
public interface UserService {

	String REQUEST_PREFIX = "sso";
	/**
	 *
	 */
	@RequestMapping(value = REQUEST_PREFIX + "/checkData")
	TaotaoResult checkData(@RequestParam("parentId") String data, @RequestParam("parentId") int type);

	@RequestMapping(value = REQUEST_PREFIX + "/register")
	TaotaoResult register(@RequestBody TbUser user);

	@RequestMapping(value = REQUEST_PREFIX + "/login")
	TaotaoResult login(@RequestParam("parentId") String username, @RequestParam("parentId") String password);

	@RequestMapping(value = REQUEST_PREFIX + "/getUserByToken")
	TaotaoResult getUserByToken(@RequestParam("parentId") String token);
}
