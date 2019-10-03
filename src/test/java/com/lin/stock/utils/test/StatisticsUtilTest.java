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
	public void should_return_57_as_intMedian(){	
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
	public void should_return_56_as_Longmedian(){	
		List<Long> list = new ArrayList<Long>();
		list.add(12L);
		list.add(56L);
		list.add(23L);
		list.add(10000L);
		list.add(88L);	
		assertTrue(56==StatisticsUtil.getLongMedian(list));
	}
	
	@Test
	public void should_return_57_as_longMedian(){	
		List<Long> list = new ArrayList<Long>();
		list.add(12L);
		list.add(71L);
		list.add(57L);
		list.add(23L);
		list.add(10000L);
		list.add(88L);	
		assertTrue(57==StatisticsUtil.getLongMedian(list));
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
