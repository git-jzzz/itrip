package cn.itrip.controller;

import cn.itrip.beans.pojo.ItripUser;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value="TestUserController",description = "用户控制器类")
public class TestUserController {

    @RequestMapping(value="/showUsers.html",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="显示所有用户信息",notes = "使用json格式返回用户列表信息")
    public Object showUsers(){
        List<ItripUser> userList=this.getAllUsers();
        return JSONArray.toJSONString(userList);
    }

    @RequestMapping(value = "/getUserByName.html",method = RequestMethod.GET)
    @ApiOperation(value = "根据名称查询用户信息",notes = "使用json格式返回用户对象")
    @ResponseBody
    public Object getUserByName(@ApiParam(name="userName",value = "用户名",required = true)
                                @RequestParam(value="userName")
                                        String userName){
        System.out.println("获取的参数 userName:"+userName);
        ItripUser user=new ItripUser(new Long(1),"jz","蒋征");
        return JSONArray.toJSONString(user);

    }
    @RequestMapping(value="/addUser.html",method = RequestMethod.POST)
    @ApiOperation(value = "增加用户",notes = "获取客户端输入的用户信息")
    @ResponseBody
    public String addUser(@ApiParam(name="user",value = "用户实体",required = true) @RequestBody ItripUser user){
        System.out.println(user.getId()+"\t"+user.getUserName());
        return "success";
    }




    public List<ItripUser> getAllUsers(){
        List<ItripUser> userList = new ArrayList<ItripUser>();
        ItripUser user1 = new ItripUser(new Long(122),"1","3");
        ItripUser user2 = new ItripUser(new Long(222),"scott","2323");
        ItripUser user3 = new ItripUser(new Long(322),"jack","jack@qq.com");
        ItripUser user4 = new ItripUser(new Long(422),"jeff","jeff@qq.com");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        return userList;
    }

}
