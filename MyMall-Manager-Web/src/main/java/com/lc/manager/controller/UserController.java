package com.lc.manager.controller;

import com.lc.common.pojo.Result;
import com.lc.common.utils.GeetestLib;
import com.lc.common.utils.ResultUtil;
import com.lc.manager.annotation.SystemControllerLog;
import com.lc.manager.pojo.TbUser;
import com.lc.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@Api(description= "管理员管理")
public class UserController {
    final static Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/geetestInit",method = RequestMethod.GET)
    @ApiOperation(value = "极验初始化")
    public String geetesrInit(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);

        resStr = gtSdk.getResponseStr();

        return resStr;
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    @SystemControllerLog(description="登录系统")
    public Result<Object> login(String username, String password,
                                String challenge, String validate, String seccode,
                                HttpServletRequest request){
        //极验验证
        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            log.info(String.valueOf(gtResult));
        } else {
            // gt-server非正常情况下，进行failback模式验证
            log.error("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            log.error(String.valueOf(gtResult));
        }

        if (gtResult == 1) {
            // 验证成功
            Subject subject = SecurityUtils.getSubject() ;
            //MD5加密
            String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            UsernamePasswordToken token = new UsernamePasswordToken(username,md5Pass);
            try {
                subject.login(token);
                return new ResultUtil<Object>().setData(null);
            }catch (Exception e){
                return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
            }
        }
        else {
            // 验证失败
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    @ApiOperation(value = "获取登录用户信息")
    public Result<TbUser> getUserInfo(){

        String username= SecurityUtils.getSubject().getPrincipal().toString();
        TbUser tbUser=userService.getUserByUsername(username);
        tbUser.setPassword(null);
        return new ResultUtil<TbUser>().setData(tbUser);
    }


}
