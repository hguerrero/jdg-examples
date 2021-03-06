package mx.redhat.datagrid.demo.websample.service.mapper;

import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;

import mx.redhat.datagrid.demo.websample.model.Country;

@SuppressWarnings("serial")
public class RegionNameMapper implements Mapper<String, Country, String, Integer> {

	@Override
	public void map(String key, Country country, Collector<String, Integer> collector) 
	{
		collector.emit(country.getRegion(), 1);
	}

}
