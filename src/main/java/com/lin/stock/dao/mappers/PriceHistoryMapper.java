package com.lin.stock.dao.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lin.stock.model.PriceHistory;

@Mapper
public interface PriceHistoryMapper {
    
	@Insert("insert into PRICE_HISTORY(pk, DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER)" 
	+" values(#{pk},#{date},#{tclose},#{high},#{low},#{topen},#{chg},#{pchg},#{turnoverrate},#{voturnover},#{vaturnover})")
    public int insert(PriceHistory priceHistory);
	
	
	@Select("select pk ,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
	+" from PRICE_HISTORY"
	+" where pk = #{pk}")
	public PriceHistory selectById(PriceHistory priceHistory);
	
}