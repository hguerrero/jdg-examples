/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mx.redhat.datagrid.demo.websample.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mx.redhat.datagrid.demo.websample.model.Country;
import mx.redhat.datagrid.demo.websample.service.WorldBean;
import mx.redhat.datagrid.demo.websample.service.WorldListing;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/world")
@RequestScoped
public class WorldRESTService {

    @Inject
    private Logger log;

    @Inject
    private WorldListing listing;
    
    @Inject
    private WorldBean bean;

    @GET
    @Path("/cache")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> listAllCountriesWithCache() {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
        try {    		
			return listing.listCountries();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			throw new WebApplicationException(500);
		} finally {
    		Date endTime = new Date();
    		log.info("End time: " + endTime);
    		log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
		}
    }

    @GET
    @Path("/cache/region/totals")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> countCountriesByRegion() {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
        try {    		
			return listing.countCountriesByRegion();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			throw new WebApplicationException(500);
		} finally {
    		Date endTime = new Date();
    		log.info("End time: " + endTime);
    		log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
		}
    }

    @GET
    @Path("/cache/region/{region:[A-Za-z]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> listRegionCountriesWithCache(@PathParam("region") String region) {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
        try {    		
			return listing.findCountriesByRegion(region);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			throw new WebApplicationException(500);
		} finally {
    		Date endTime = new Date();
    		log.info("End time: " + endTime);
    		log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
		}
    }

    @GET
    @Path("/cache/{code:[A-Z]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Country lookupCountryByIdWithCache(@PathParam("code") String code) {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
        Country country = listing.findCountryByCode(code);
        if (country == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		Date endTime = new Date();
		log.info("End time: " + endTime);
		log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
        return country;
    }

    @GET
    @Path("/nocache")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> listAllCountriesNoCache() {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
    	try {
    		return bean.listAllCountries();
    	} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			throw new WebApplicationException(500);
		} finally {
			Date endTime = new Date();
			log.info("End time: " + endTime);
			log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
		}
    }

    @GET
    @Path("/nocache/{code:[A-Z]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Country lookupCountryByIdNoCache(@PathParam("code") String code) {
		Date initTime = new Date();
		log.info("Start time: " + initTime);
        Country country = bean.findCountry(code);
        if (country == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		Date endTime = new Date();
		log.info("End time: " + endTime);
		log.info("Elapsed time: " + (endTime.getTime() - initTime.getTime()) + "ms");
        return country;
    }

    @GET
    @Path("/put-cache")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCountryWithCache() {

        Response.ResponseBuilder builder = null;

        try {
        	Country myCountry = new Country();
        	myCountry.setCode("ZZZ");
        	myCountry.setName("Swazilandia");
        	myCountry.setContinent("North America");
        	myCountry.setRegion("Central America");
        	myCountry.setSurfacearea(1.0f);
        	myCountry.setPopulation(1);
        	myCountry.setLocalname("swasi");
        	myCountry.setGovernmentform("Teocratic");
        	myCountry.setCode2("Z1");
        	
            listing.add(myCountry);

            // Create an "ok" response
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }


    @GET
    @Path("/put-nocache")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCountryNoCache() {

        Response.ResponseBuilder builder = null;

        try {
        	Country myCountry = new Country();
        	myCountry.setCode("ZZZ");
        	myCountry.setName("Swazilandia");
        	myCountry.setContinent("North America");
        	myCountry.setRegion("Central America");
        	myCountry.setSurfacearea(1.0f);
        	myCountry.setPopulation(1);
        	myCountry.setLocalname("swasi");
        	myCountry.setGovernmentform("Teocratic");
        	myCountry.setCode2("Z1");
        	
            bean.add(myCountry);

            // Create an "ok" response
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
}
