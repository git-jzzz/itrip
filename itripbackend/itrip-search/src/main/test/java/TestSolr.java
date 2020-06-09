import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.solr.service.HotelService;
import cn.itrip.solr.service.HotelServiceImpl;

import java.util.List;

public class TestSolr {
    public static void main(String[] args) {
        HotelService hotelService=new HotelServiceImpl();
        List<ItripHotel> hotelList =hotelService.queryHotelList("北京");
        for(ItripHotel hotel:hotelList){
            System.out.print("酒店名:"+hotel.getHotelName()+"\t酒店地址:"+hotel.getAddress()+"\n");
        }
    }
}
