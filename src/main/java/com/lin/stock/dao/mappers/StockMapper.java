package com.lin.stock.dao.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lin.stock.model.Stock;

@Mapper
public interface StockMapper {

	
	@Select("select code"
	+ " from STOCK")
	public List<Stock> selectAll();
}