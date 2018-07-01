package com.bow.cache.facade;


/**
 * @author vv
 * @since 2018/7/1.
 */
public interface ICache<K, V> extends Iterable<ICache.Entry<K, V>> {

    V get(K key);

	interface Entry<K, V> {

		/**
		 * Returns the key of this mapping
		 *
		 * @return the key, not {@code null}
		 */
		K getKey();

		/**
		 * Returns the value of this mapping
		 *
		 * @return the value, not {@code null}
		 */
		V getValue();
	}
}
