package com.bow.ignite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.cache.CacheException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bow.utils.StringUtil;

public class LocalCacheImpl<K, V> implements ICache<K, V> {
    /**
     * 日志对象
     */
    private static final Logger TRACER = LoggerFactory.getLogger(LocalCacheImpl.class);

    /**
     * 锁
     */
    private static ReadWriteLock rwLocker = new ReentrantReadWriteLock();

    private Map<K, V> cacheMap = new ConcurrentHashMap<>();

    @Override
    public void initCache(String cacheTypeName, boolean createCfg) throws CacheException {

    }

    @Override
    public V get(K key) {
        V objValue = null;
        rwLocker.readLock().lock();
        try {
            objValue = cacheMap.get(key);
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.readLock().unlock();
        }
        return objValue;
    }

    @Override
    public Map<K, V> getAll(Set<K> keys) {
        Map<K, V> retMap = new HashMap<>();
        rwLocker.readLock().lock();
        try {
            HashMap<K, V> result = new HashMap<>();
            for (K key : keys) {
                result.put(key, get(key));
            }
            return result;
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.readLock().unlock();
        }
        return retMap;
    }

    @Override
    public Map<K, V> getAll() {
        return getAll(cacheMap.keySet());
    }

    @Override
    public void put(K key, V value) {
        rwLocker.writeLock().lock();
        try {
            cacheMap.put(key, value);
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.writeLock().unlock();
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V retObj = get(key);
        if (StringUtil.isEmpty(retObj)) {
            put(key, value);
            retObj = value;
        }
        return retObj;
    }

    @Override
    public void putAll(Map<K, V> inputMap) {
        rwLocker.writeLock().lock();
        try {
            for (Map.Entry<K, V> entry : inputMap.entrySet()) {
                cacheMap.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.writeLock().unlock();
        }
    }

    @Override
    public V remove(K key) {
        V objValue = null;
        rwLocker.writeLock().lock();
        try {
            objValue = cacheMap.remove(key);
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.writeLock().unlock();
        }
        return objValue;
    }

    @Override
    public void removeAll(Set<K> keys) {
        rwLocker.writeLock().lock();
        try {
            for (K key : keys) {
                cacheMap.remove(key);
            }
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.writeLock().unlock();
        }
    }

    @Override
    public void clear() {
        rwLocker.writeLock().lock();
        try {
            cacheMap.clear();
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.writeLock().unlock();
        }
    }

    @Override
    public int size() {
        int size = 0;
        rwLocker.readLock().lock();
        try {
            size = cacheMap.size();
        } catch (Exception e) {
            TRACER.error(e.getMessage(), e);
        } finally {
            rwLocker.readLock().unlock();
        }
        return size;
    }
}
