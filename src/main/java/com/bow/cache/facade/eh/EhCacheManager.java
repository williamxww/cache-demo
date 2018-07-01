package com.bow.cache.facade.eh;

import com.bow.cache.facade.ICache;
import com.bow.cache.facade.ICacheManager;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * @author vv
 * @since 2018/7/1.
 */
public class EhCacheManager implements ICacheManager {

    private static EhCacheManager manager = new EhCacheManager();

    private CacheManager cacheManager;

    public EhCacheManager(){
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
                        String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
    }

    public static ICacheManager getInstance(){
        return manager;
    }

    @Override
    public <K, V> ICache<K, V> createCache(String name) {
        return null;
    }

    @Override
    public <K, V> ICache<K, V> cache(String name) {
        cacheManager.getCache(name, Object.class, Object.class);
        return null;
    }
}
