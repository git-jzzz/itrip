package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import cn.itrip.service.TokenService;
import cn.itrip.service.UserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Map;

@Controller
@Api(value = "LoginController",description = "登录控制器")
@RequestMapping("/api")
public class LoginController {



   @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "登录验证",notes = "返回dto对象")
    @ResponseBody
    @RequestMapping(value = "/dologin",method= RequestMethod.POST,produces = "application/json")
    public   Dto login(@ApiParam(name="name",value = "用户名") @RequestParam String name,
                      @ApiParam(name="password",value = "密码") @RequestParam String password, HttpServletRequest request){
        try {
            ItripUser user =userService.login(name, MD5.getMd5(password,32));
            if(EmptyUtils.isNotEmpty(user)){
                String userAgent=request.getHeader("user-agent");
                String token=tokenService.generateToken(userAgent,user);
                tokenService.save(token,user);
                //token 过期时间  生成时间
                ItripTokenVO vo=new ItripTokenVO(token,Calendar.getInstance().getTimeInMillis()+2*60*60*1000, Calendar.getInstance().getTimeInMillis());
                return DtoUtil.returnDataSuccess(vo);
            }else{
                return DtoUtil.returnFail("用户密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }

    }



}

