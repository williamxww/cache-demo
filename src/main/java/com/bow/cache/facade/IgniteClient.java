package com.bow.cache.facade;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * @author vv
 * @since 2018/7/1.
 */
public class IgniteClient implements ICacheManager, ITransactionManager {

    private static IgniteClient manager = new IgniteClient();

    private Ignite ignite;

    public IgniteClient(){
        ignite = Ignition.start();
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
        return null;
    }

    @Override
    public ITransaction txStart() {
        return null;
    }
}
