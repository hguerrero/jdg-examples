package com.jboss.samples.datagrid.websample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.distexec.mapreduce.MapReduceTask;
import org.infinispan.manager.CacheContainer;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.ResultIterator;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;

import com.jboss.samples.datagrid.websample.model.Country;
import com.jboss.samples.datagrid.websample.service.mapper.RegionNameMapper;
import com.jboss.samples.datagrid.websample.service.mapper.RegionNameReducer;


public class WorldListing {

	@Inject
	private Logger log;
	
    @Inject
    private CacheContainer provider;
	
	public List<Country> listCountries() throws Exception 
	{
		Cache<String, Country> cache = provider.getCache("countryCache");
		
//		if (cache.size() != 0)
//			cache = cache.getAdvancedCache().withFlags(Flag.SKIP_CACHE_LOAD);

//		Set<Entry<String, Country>> entries = cache.entrySet();
		Set<String> entries = cache.keySet();
		
		List<Country> countries = new ArrayList<Country>();
		
		for (String entry : entries) {
			countries.add(cache.get(entry));
		}
		
		return countries;
	}
	
	public Map<String, Integer> countCountriesByRegion() throws Exception 
	{
		Cache<String, Country> cache = provider.getCache("countryCache");

		MapReduceTask<String, Country, String, Integer> task = new MapReduceTask<String, Country, String, Integer>(cache);
        
        task.mappedWith(new RegionNameMapper()).reducedWith(new RegionNameReducer());
        
        Map<String, Integer> countCountriesByRegion = task.execute();
		
		return countCountriesByRegion;
	}
	
	public List<Country> findCountriesByRegion(String region) throws Exception 
	{
		log.info("Looking for region: " + region);
		
		Cache<String, Country> cache = provider.getCache("countryCache");

		SearchManager manager = Search.getSearchManager(cache);
		
		QueryBuilder builder = manager.buildQueryBuilderForClass(Country.class).get();
		Query luceneQuery = builder.keyword()
				.onField("region")
				.matching(region).createQuery();
		
		CacheQuery query = manager.getQuery(luceneQuery);
		
		List<Country> countries = new ArrayList<Country>();
		
		ResultIterator miIt = query.iterator();
		while (miIt.hasNext()){
			countries.add((Country)miIt.next());
		}
		
		return countries;
	}
	
	public Country findCountryByCode(String code) 
	{
		Cache<String, Country> cache = provider.getCache("countryCache");
		
        return cache.get(code);
    }

	public boolean add(Country myCountry) 
	{
		Cache<String, Country> cache = provider.getCache("countryCache");
		
		cache.put(myCountry.getCode(), myCountry);
		
		return cache.containsKey(myCountry.getCode());
	}

}
