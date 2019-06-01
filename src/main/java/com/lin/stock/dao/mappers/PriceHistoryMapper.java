package com.lin.stock.dao.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lin.stock.model.PriceHistory;

@Mapper
public interface PriceHistoryMapper {
    
	@Insert("insert into PRICE_HISTORY(pk, CODE, DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER)" 
	+" values(#{pk},#{code},#{date},#{tclose},#{high},#{low},#{topen},#{chg},#{pchg},#{turnoverrate},#{voturnover},#{vaturnover})")
    public int insert(PriceHistory priceHistory);
	
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
	+" from PRICE_HISTORY"
	+" where pk = #{pk}")
	public PriceHistory selectById(PriceHistory priceHistory);
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
	+ " from PRICE_HISTORY"
	+ " where CODE = #{code}"
	+ " and DATE = #{date}")
	public PriceHistory selectByStockCodeAndDate(PriceHistory priceHistory);
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
	+ " from PRICE_HISTORY"
	+ " where CODE = #{stockCode}"
	+ " and DATE >= #{beginDate}"
	+ " and DATE <= #{endDate}"
	+ " order by CODE, DATE")
	public List<PriceHistory> selectByStockCodeAndDateRange(String stockCode, String beginDate, String endDate);
		

}