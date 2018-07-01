package com.bow.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
						String.class, ResourcePoolsBuilder.heap(10)))
				.build();
		cacheManager.init();
		Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);
		Cache<Integer, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(10)).build());

		for (int i = 0; i <= 20; i++) {
			// 写
			myCache.put(i, "@" + i);
			// 读
			String value = myCache.get(i);
			System.out.println("get at " + i + ":" + value);
		}

		cacheManager.removeCache("preConfigured");
		cacheManager.close();
	}
}
