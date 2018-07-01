package com.bow.cache.facade;

import com.bow.cache.facade.eh.EhCacheManager;

/**
 * @author vv
 * @since 2018/7/1.
 */
public class Sample {

    public void ignite(){
        ICacheManager cacheManager = IgniteClient.getInstance();
        ICache cache = cacheManager.cache("demo");
    }

    public void ehcache(){
        ICacheManager cacheManager = EhCacheManager.getInstance();
        ICache cache = cacheManager.cache("demo");
    }
}
