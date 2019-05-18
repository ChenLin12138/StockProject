package com.lin.stock.dao.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.lin.stock.model.PriceHistory;

@Mapper
public interface PriceHistoryMapper {
    
	@Insert("insert into PRICE_HISTORY(DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER)"+ 
	"values('19000101','1','1','1','1','1','1','1','1','1')")
    int insert(PriceHistory record);

}