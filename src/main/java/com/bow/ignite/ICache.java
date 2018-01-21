package com.bow.ignite;

import java.util.Map;
import java.util.Set;

import javax.cache.CacheException;

/**
 * 缓存类型接口
 */
public interface ICache<K, V> {
	/**
	 * 初始化缓存
	 *
	 * @param cacheName 缓存名称
	 * @param createCfg 当缓存配置不存在时，是否根据名字创建
	 * @throws CacheException 缓存初始化异常
	 */
	void initCache(String cacheName, boolean createCfg) throws CacheException;

	/**
	 * 获取缓存值
	 * 
	 * @param key key
	 * @return V
	 */
	V get(K key);

	/**
	 * 获取指定keys集合的值
	 * 
	 * @param keys key集合
	 * @return Map
	 */
	Map<K, V> getAll(Set<K> keys);

	/**
	 * 获取所有缓存值
	 * 
	 * @return Map
	 */
	Map<K, V> getAll();

	/**
	 * 设置缓存值
	 * 
	 * @param key 缓存key
	 * @param value 缓存value
	 */
	void put(K key, V value);

	/**
	 * 如果值不存在时，设置缓存值
	 * 
	 * @param key 缓存key
	 * @param value 缓存value
	 * @return V 缓存value
	 */
	V putIfAbsent(K key, V value);

	/**
	 * 批量设置缓存值
	 * 
	 * @param map 缓存值
	 */
	void putAll(Map<K, V> map);

	/**
	 * 移除缓存
	 * 
	 * @param key key
	 * @return V 缓存值
	 */
	V remove(K key);

	/**
	 * 批量移除缓存
	 * 
	 * @param keys key集合
	 */
	void removeAll(Set<K> keys);

	/**
	 * 清空缓存
	 */
	void clear();

	/**
	 * 获取缓存大小
	 * 
	 * @return int
	 */
	int size();
}
