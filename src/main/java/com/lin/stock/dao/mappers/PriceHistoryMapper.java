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
			+" from PRICE_HISTORY"
			+" where CODE = #{stockCode}"
			+" order by date limit 1")
	public PriceHistory selectFirstBusinessInfo(String stockCode);
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where CODE = #{stockCode}"
			+" order by date")
	public List<PriceHistory> selectPriceHistoryByStockCode(String stockCode);
	
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
	+ " order by DATE")
	public List<PriceHistory> selectByStockCodeAndDateRange(String stockCode, String beginDate, String endDate);
		
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where date >= #{beginDate}"
			+ " and date <= #{endDate}"
			+ " order by CODE, DATE")
	public List<PriceHistory> selectByDateRange(String beginDate, String endDate);
	
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where CODE = #{stockCode}"
			+" and Date < #{date}"
			+" order by date desc limit #{days}")
	public List<PriceHistory> selectPreviousDatesInfo(String stockCode, String date, int days);
	
	
	@Select("select pk, CODE,DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where CODE = #{stockCode}"
			+" and Date > #{date}"
			+" order by date limit #{days}")
	public List<PriceHistory> selectNextDatesInfo(String stockCode, String date, int days);


	@Select("select pk, CODE, DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where CODE = #{stockCode}"
			+" and Date <= #{date}"
			+" order by date desc limit #{days}")
	public List<PriceHistory> selectLastInfoByDate(String stockCode, String date, int days);
	
	
	@Select("select pk, CODE, DATE, TCLOSE, HIGH, LOW, TOPEN, CHG, PCHG, TURNOVERRATE, VOTURNOVER, VATURNOVER"
			+" from PRICE_HISTORY"
			+" where Date >= #{fromDate}"
			+" Date <= #{toDate}"
			+" order by CODE, DATE")
	public List<PriceHistory> selectPriceHistoriesByFromToDate(String fromDate, String toDate);
	
	
	@Select("select DISTINCT code from PRICE_HISTORY")
	public List<String> selectAllCodes();
	
	@Select("select date from PRICE_HISTORY"
			+ " WHERE CODE = #{stockCode}")
	public List<String> selectAllDatesByStockCode(String stockCode);
}