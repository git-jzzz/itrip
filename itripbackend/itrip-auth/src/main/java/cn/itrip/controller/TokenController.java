package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
@Api(value = "TokenCotroller",description = "token管理")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @ApiOperation(value="验证Token是否有效",notes = "返回验证信息")
    @ResponseBody
    @RequestMapping(value="/validataToken",method = RequestMethod.GET,produces = "application/json",headers = "token")
    public Dto validate(HttpServletRequest request){
        try {
            boolean result=tokenService.validate(request.getHeader("user-agent"),request.getHeader("token"));
            if(result){
                return DtoUtil.returnSuccess("token有效");
            }else{
                return DtoUtil.returnSuccess("token无效");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }

    @ResponseBody
    @ApiOperation(value="退出系统",notes = "返回验证信息")
    @RequestMapping(value="/logout",produces = "application/json",method = RequestMethod.GET,headers = "token")
    public Dto loginout(HttpServletRequest request){
        try {
            String token=request.getHeader("token");
        if(tokenService.validate(request.getHeader("user-agent"),token)){
            long result=tokenService.delete(token);
            System.out.println(result);
            return DtoUtil.returnSuccess(result+"");
        }else{
            return DtoUtil.returnFail("token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return DtoUtil.returnFail("退出失败",ErrorCode.AUTH_TOKEN_INVALID);
    }
    }

    @ApiOperation(value="置换token",notes = "返回json消息")
    @ResponseBody
    @RequestMapping(value="/retoken",method = RequestMethod.POST,produces = "application/json",headers = "token")
    public  Dto reloadToken(HttpServletRequest request){
        String token= null;
        try {
            token = tokenService.reloadToken(request.getHeader("user-agent"),request.getHeader("token"));
            ItripTokenVO vo=new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis()+2*60*60*1000, Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getLocalizedMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }
}
