package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.service.TestService;
import cn.itrip.solr.service.HotelService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Api(value="HotelSearchController",description = "酒店搜索查询控制器")
@RequestMapping("/api/hotellist")
@Controller
public class HotelSearchController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TestService testService;

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @ApiOperation(value="根据热门城市查询6个酒店",notes = "依据输入的参数进行solr查询")
    @ResponseBody
    @RequestMapping(value="/searchItripHotelListByHotCity",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public Dto<Object> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO vo){
        List<ItripHotel> list=null;
        try {
            list=testService.queryHotelByCity(vo.getCityId(),vo.getCount());
        } catch (IOException e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),"100503");
        } catch (SolrServerException e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),"100503");
        }
        return DtoUtil.returnSuccess("查询酒店成功",list);
    }

    @ApiOperation(value="依据热门城市查询酒店分页",notes = "依据输入的参数进行solr查询")
    @RequestMapping(value="/searchItripHotelPage",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public Dto<Object> selectHotelByKeyWord(@RequestBody SearchHotelVO vo/*@ApiParam(name="keyword",value="输入框") @RequestParam String keyword,@ApiParam(name="pageNo",value="当前页") @RequestParam String pageNo*/){
        Page<ItripHotel> page=new Page<ItripHotel>();
        try {
            //分页设置
            page.setPageSize(3);
            String k=vo.getTradeAreaIds()==null?"":vo.getTradeAreaIds();
            page.setTotal(testService.queryCount(vo.getKeywords()+k,vo.getDestination(),vo.getHotelLevel()));
if(EmptyUtils.isEmpty(page.getTotal())){
    return DtoUtil.returnFail("无数据","100503");
}
            int curPage=vo.getPageNo()!=null?vo.getPageNo():1;
            if(curPage>page.getPageCount()){
                curPage=page.getPageCount();
            }
            if(curPage<0){
                curPage=1;
            }
            page.setCurPage(curPage);
         page.setRows( testService.queryHotelList(vo.getKeywords()+k,vo.getDestination(),page,vo.getHotelLevel()));
        } catch (IOException e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),"100503");
        } catch (SolrServerException e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),"100503");
        }
        return DtoUtil.returnSuccess("获取酒店分页成功",page);
    }
}
