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
package com.jboss.samples.datagrid.websample.session;

import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;


/**
 * {@link CacheContainerProvider}'s implementation creating a DefaultCacheManager 
 * which is configured programmatically. Infinispan's libraries need to be bundled 
 * with the application - this is called "library" mode.
 * 
 * 
 * 
 */
@ApplicationScoped
public class JPACacheContainerProvider extends CacheContainerProvider {

	// ************************************************************************************************************
	// This should point to the Infinispan configuration file.  Either an absolute path or the name of a config
	// file in your classpath could be used.  See http://community.jboss.org/wiki/Configuringcache for more details.
	// ************************************************************************************************************

	// This skeleton project ships with 4 different Infinispan configurations.  Uncomment the one most appropriate to you.
	private static final String INFINISPAN_CONFIGURATION = "infinispan-jpa.xml";

	@Inject
	private Logger log;

	private EmbeddedCacheManager manager;

	@Produces
	public CacheContainer getCacheContainer() {
		if (manager == null) {
			try {
				manager = new DefaultCacheManager(INFINISPAN_CONFIGURATION, true);
			} catch (Exception e) {
				throw new RuntimeException("Unable to configure Infinispan", e);
			}
			log.info("=== Using DefaultCacheManager (library mode) ===");
		}
		return manager;
	}

	/**
	 * Retrieves the default cache.
	 * @param <K> type used as keys in this cache
	 * @param <V> type used as values in this cache
	 * @return a cache
	 */
	public <K, V> Cache<K, V> getCache() {
		return manager.getCache();
	}

	/**
	 * Retrieves a named cache.
	 * @param cacheName name of cache to retrieve
	 * @param <K> type used as keys in this cache
	 * @param <V> type used as values in this cache
	 * @return a cache
	 */
	public <K, V> Cache<K, V> getCache(String cacheName) {
		if (cacheName == null) throw new NullPointerException("Cache name cannot be null!");
		return manager.getCache(cacheName);
	}
	
	@PreDestroy
	public void cleanUp() {
		manager.stop();
		manager = null;
	}
}