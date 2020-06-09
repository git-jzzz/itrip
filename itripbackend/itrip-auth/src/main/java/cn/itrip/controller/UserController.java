package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import cn.itrip.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(value="UserController",description = "用户管理")
@Controller
@RequestMapping(value="/api")
public class UserController {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }



   @RequestMapping(value="/ckusr",method = RequestMethod.GET)
   @ResponseBody
    public Dto<Object> ckusr(@RequestParam String name){
       if(EmptyUtils.isNotEmpty(userService.findUserByUserCode(name))){
           return DtoUtil.returnFail("邮箱已注册","0000");
       }
        return DtoUtil.returnSuccess("可注册");

    }


    @ApiOperation(value = "邮箱注册",notes = "返回信息")
    /**
     * 邮箱注册
     * @param vo
     * @return
     */
    @RequestMapping(value="/doregister",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doRedister(@RequestBody ItripUserVO vo){

        //邮箱验证
        if(!this.checkEmail(vo.getUserCode())){
            return DtoUtil.returnFail("请使用正确的邮箱地址", ErrorCode.AUTH_ILLEGAL_USERCODE);
        }
        //调用业务层createuser


        if(userService.findUserByUserCode(vo.getUserCode())!=null){
            //usercode存在
            return DtoUtil.returnFail("用户已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
    }else{
            try {
                ItripUser user=new ItripUser();
                user.setUserCode(vo.getUserCode());
                user.setUserPassword(MD5.getMd5(vo.getUserPassword(),32));
                user.setUserName(vo.getUserName());
                userService.itriptxCreateUser(user);
                return DtoUtil.returnSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
            }
        }
    }

    @ApiOperation(value="邮箱激活验证",notes = "激活信息")
    @RequestMapping(value="/activate",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto activate(@ApiParam(name="user",value="邮箱") @RequestParam String user,@ApiParam(name="code",value="激活码") @RequestParam String code){

        try {
             if(userService.activate(user,code)){
                 return DtoUtil.returnSuccess("激活成功");
             }else{
                return DtoUtil.returnSuccess("激活失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("激活失败",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
            String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).find();
    }

    /**
     * 验证是否合法手机号
     * @param phone
     * @return
     */
    public boolean validPhone(String phone){
        String regex="^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }

    @ApiOperation(value = "手机注册",notes = "返回信息")
    /**
     * 手机注册
     * @param vo
     * @return
     */
    @RequestMapping(value="/registerbyphone",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doRedisterByPhone(@RequestBody ItripUserVO vo){
        //1.手机号验证
        if(!this.validPhone(vo.getUserCode())){
        return DtoUtil.returnFail("请使用正确的手机号码!",ErrorCode.AUTH_ILLEGAL_USERCODE);
        }
        //2.调用service

        if(userService.findUserByUserCode(vo.getUserCode())!=null){
            //usercode存在
            return DtoUtil.returnFail("用户已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }else {
            try {
                ItripUser user=new ItripUser();
                user.setUserCode(vo.getUserCode());
                user.setUserPassword(MD5.getMd5(vo.getUserPassword(),32));
                user.setUserName(vo.getUserName());
                userService.itriptxCreateUserByPhone(user);
                return DtoUtil.returnSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
            }
        }

    }

    @ApiOperation(value="手机激活验证",notes = "验证信息")
    @RequestMapping(value="/validatephone",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto activatePhone(@ApiParam(name="user",value="手机号码") @RequestParam String user,@ApiParam(name="code",value="验证码") @RequestParam String code){

        try {
            if(userService.validatePhone(user,code)){
                return DtoUtil.returnSuccess("验证成功");
            }else{
                return DtoUtil.returnSuccess("验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("验证失败",ErrorCode.AUTH_UNKNOWN);
        }
    }


}
