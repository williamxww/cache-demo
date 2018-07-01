package com.bow.cache.facade;

/**
 * @author vv
 * @since 2018/7/1.
 */
public interface ITransaction {

    void commit();

    void rollback();
}
