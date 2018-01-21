package com.bow.ignite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 缓存实现基础类
 */
public abstract class BaseCache<K, V> implements ICacheable<K, V> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCache.class);
	/**
	 * syncObj
	 */
	private final Object syncObj = new Object();
	/**
	 * cache实现
	 */
	private ICache<K, V> cacheImpl;

	@Override
	public ICache getCacheImpl() {
		synchronized (syncObj) {
			if (cacheImpl == null) {
				init();
			}
		}
		return cacheImpl;
	}

	public void setCacheImpl(ICache<K, V> cacheImpl) {
		this.cacheImpl = cacheImpl;
	}

	@Override
	public abstract V getFreshValue(K cacheKey);

	/**
	 * 初始化缓存实现bean
	 */
	protected void init() {
		cacheImpl.initCache("", true);
	}
}
