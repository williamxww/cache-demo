package com.bow.ignite;

/**
 * 缓存具体接口
 * 
 */
public interface ICacheable<K, V> {
	/**
	 * 获取cache实现
	 * 
	 * @return ICache
	 */
	ICache getCacheImpl();

	/**
	 * 获取cache Value
	 * 
	 * @param cacheKey key
	 * @return V
	 */
	V getFreshValue(K cacheKey);
}
