package com.bow.ignite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheException;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bow.utils.StringUtil;

public class IgniteCacheImpl<K, V> implements ICache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IgniteCacheImpl.class);

    private Ignite ignite;

    private IgniteManager igniteManager;

    /**
     * 缓存名称
     */
    private CacheConfiguration<K, V> cacheCfg;

    /**
     * 当缓存配置不存在时，是否根据名字创建
     */
    private boolean createCfg = true;

    @Override
    public void initCache(String cacheName, boolean createCfg) throws CacheException {
        this.ignite = igniteManager.getIgnite();
        if (StringUtil.isNotEmpty(cacheName)) {
            this.cacheCfg = new CacheConfiguration<>(cacheName);
            this.createCfg = createCfg;
            this.ignite.getOrCreateCache(cacheName);
        } else {
            throw new CacheException("Cache name cannot be empty!");
        }
    }

    /**
     * 根据缓存配置初始化缓存
     *
     * @param cacheCfg 缓存配置
     * @throws CacheException 缓存初始化异常
     */
    public void initCache(CacheConfiguration<K, V> cacheCfg) throws CacheException {
        this.ignite = igniteManager.getIgnite();
        if (StringUtil.isNotEmpty(cacheCfg)) {
            this.cacheCfg = cacheCfg;
            this.ignite.getOrCreateCache(cacheCfg);
        } else {
            throw new CacheException("Cache name cannot be empty!");
        }
    }

    @Override
    public V get(K key) {
        V retObj = null;
        try {
            IgniteCache<K, V> igniteCache = getCache();
            retObj = igniteCache.get(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return retObj;
    }

    @Override
    public Map<K, V> getAll(Set<K> keys) {
        Map<K, V> retMap = null;
        try {
            IgniteCache<K, V> igniteCache = getCache();
            retMap = igniteCache.getAll(keys);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return retMap;
    }

    /**
     * please implement in specified ignite cacheable
     *
     * @return Map
     */
    @Override
    public Map<K, V> getAll() {
        Map<K, V> retMap = new HashMap<>();
        try {
            IgniteCache<K, V> igniteCache = getCache();
            Iterator<Cache.Entry<K, V>> cacheEntries = igniteCache.iterator();
            if (cacheEntries != null) {
                while (cacheEntries.hasNext()) {
                    Cache.Entry<K, V> entry = cacheEntries.next();
                    retMap.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return retMap;
    }

    @Override
    public void put(K key, V value) {
        try {
            IgniteCache<K, V> igniteCache = getCache();
            igniteCache.put(key, value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V retObj = null;
        try {
            IgniteCache<K, V> igniteCache = getCache();
            V oldValue = igniteCache.getAndPutIfAbsent(key, value);
            retObj = oldValue != null ? oldValue : value;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return retObj;
    }

    @Override
    public void putAll(Map<K, V> map) {
        try {
            IgniteCache<K, V> igniteCache = getCache();
            igniteCache.putAll(map);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public V remove(K key) {
        V retObj = null;
        try {
            IgniteCache<K, V> igniteCache = getCache();
            retObj = igniteCache.getAndRemove(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return retObj;
    }

    @Override
    public void removeAll(Set<K> keys) {
        try {
            IgniteCache<K, V> igniteCache = getCache();
            igniteCache.removeAll(keys);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void clear() {
        try {
            IgniteCache<K, V> igniteCache = getCache();
            igniteCache.clear();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public int size() {
        int size = 0;
        try {
            IgniteCache<K, V> igniteCache = getCache();
            size = igniteCache.size();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return size;
    }

    /**
     * 获取缓存对象，可能会抛出
     *
     * @return
     */
    private IgniteCache<K, V> getCache() {
        if (this.createCfg) {
            return this.ignite.getOrCreateCache(cacheCfg);
        } else {
            return this.ignite.cache(cacheCfg.getName());
        }
    }
}
