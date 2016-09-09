package com.jboss.samples.datagrid.websample.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jboss.samples.datagrid.websample.model.Country;

@Stateless
public class WorldBean 
{
	@Inject
	private Logger log;
	
    @PersistenceContext(unitName = "nocache")
    private EntityManager manager;

	public List<Country> listAllCountries() 
	{
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Country> criteria = cb.createQuery(Country.class);
		Root<Country> member = criteria.from(Country.class);
		criteria.select(member).orderBy(cb.asc(member.get("code")));
		return manager.createQuery(criteria).getResultList();
	}

	public Country findCountry(String code) 
	{
		log.info("Looking for: " + code);
		
		return manager.find(Country.class, code);
	}

	public void add(Country myCountry) 
	{
		manager.persist(myCountry);
		
		log.info("Persisted: " + myCountry.getName());
	}


}
