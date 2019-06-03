package com.lin.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.stock.dao.mappers.StockMapper;
import com.lin.stock.model.Stock;

/**
 * @author Chen Lin
 * @date 2019-06-03
 */

@Service
public class StockService {

	@Autowired
	private StockMapper mapper;
	
	public List<Stock> getAllStock(){
		return mapper.selectAll();
	}
	
}
