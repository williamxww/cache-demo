package com.bow.cache.facade;

import java.util.concurrent.locks.Lock;

/**
 * @author vv
 * @since 2018/7/1.
 */
public interface LockManager {

    Lock lock();

}
