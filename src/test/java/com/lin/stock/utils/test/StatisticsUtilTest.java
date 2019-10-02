package com.lin.stock.utils.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lin.stock.utils.StatisticsUtil;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

public class StatisticsUtilTest {

	@Test
	public void should_return_56_as_median(){	
		List<Integer> list = new ArrayList<Integer>();
		list.add(12);
		list.add(56);
		list.add(23);
		list.add(10000);
		list.add(88);	
		assertTrue(56==StatisticsUtil.getIntMedian(list));
	}
	
	@Test
	public void should_return_57_as_median(){	
		List<Integer> list = new ArrayList<Integer>();
		list.add(12);
		list.add(71);
		list.add(57);
		list.add(23);
		list.add(10000);
		list.add(88);	
		assertTrue(57==StatisticsUtil.getIntMedian(list));
	}
	
	@Test
	public void should_return_2036do16_as_average(){
		List<Float> list = new ArrayList<Float>();
		list.add(12.00f);
		list.add(56.12f);
		list.add(23.12f);
		list.add(10000.67f);
		list.add(88.88f);	
		assertTrue(2036.16f==StatisticsUtil.getFloatAverageAround2(list));
	}
}
