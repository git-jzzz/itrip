package cn.itrip.dao.user;
import cn.itrip.beans.pojo.ItripUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripUserMapper {

	public ItripUser getItripUserById(@Param(value = "id") Long id)throws Exception;

	public List<ItripUser> getItripUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripUser(ItripUser itripUser)throws Exception;

	public Integer updateItripUser(ItripUser itripUser)throws Exception;

	public Integer deleteItripUserById(@Param(value = "id") Long id)throws Exception;

	/**
	 * 依据用户编码返回用户信息
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public ItripUser findUserByUserCode(@Param(value="userCode")String userCode) throws  Exception;

}
