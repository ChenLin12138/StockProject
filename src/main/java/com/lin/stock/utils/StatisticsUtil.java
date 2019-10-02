package com.lin.stock.utils;

import java.util.Collections;
import java.util.List;

/**
 * @author Chen Lin
 * @date 2019-10-02
 */

public class StatisticsUtil {

	public static Integer getIntMedian(List<Integer> list) {
		Integer result  = 0;
	    Collections.sort(list);
	    int size = list.size();
	    result = list.get((size-1)/2);
		return result;
	}

	public static float getFloatAverageAround2(List<Float> list) {
		Double result =  list.stream().mapToDouble(Float::doubleValue).average().getAsDouble();
		return (float)Math.round(result*100)/100;
				
	}
}
