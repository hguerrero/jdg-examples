package com.jboss.samples.datagrid.websample.service.mapper;

import java.util.Iterator;

import org.infinispan.distexec.mapreduce.Reducer;

@SuppressWarnings("serial")
public class RegionNameReducer implements Reducer<String, Integer> {

	@Override
	public Integer reduce(String key, Iterator<Integer> iter) 
	{
		Integer count = 0, i = 0;
		
		while (iter.hasNext()) 
		{
			i = iter.next();
			
			count += i;
		}
		
		return count;
	}

}
