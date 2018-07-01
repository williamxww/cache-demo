package com.bow.cache.facade;


/**
 * @author vv
 * @since 2018/7/1.
 */
public interface ICacheManager {

    <K, V> ICache<K, V> createCache(String name);

    <K, V> ICache<K, V> cache(String name);
}
