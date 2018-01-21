package com.bow.ignite;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class IgniteManager implements InitializingBean {

    /**
     * 是否嵌入式启动
     */
    private boolean embeddedStart = true;

    /**
     * ignite 对象
     */
    private Ignite ignite = null;

    /**
     * 是否为客户端模式启动, 可以在ignite.xml配置文件中override
     */
    private boolean clientMode = true;

    /**
     * ignite spring 配置文件
     */
    private Resource springCfg = null;

    /**
     * 初始化ignite对象
     */
    public void afterPropertiesSet() {
        if (!embeddedStart) {
            return;
        }
        if (ignite == null) {
            InputStream inputStream = null;
            Ignition.setClientMode(clientMode);
            if (springCfg != null) {
                try {
                    inputStream = springCfg.getInputStream();
                    ignite = Ignition.start(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException("Ignite spring cfg is not existed.");
                }
            } else {
                // spring cfg is empty, will auto regard current as server node
                ignite = Ignition.start();
            }
        }
    }

    /**
     * 获取ignite对象
     * 
     * @return Ignite
     */
    public Ignite getIgnite() {
        return this.ignite;
    }

    /**
     * 设置是否为客户端模式
     * 
     * @param clientMode true/false
     */
    public void setClientMode(boolean clientMode) {
        this.clientMode = clientMode;
    }

    /**
     * 设置 ignite spring 配置文件路径
     * 
     * @param springCfg spring 配置文件
     */
    public void setSpringCfg(Resource springCfg) {
        this.springCfg = springCfg;
    }

    /**
     * 是否嵌入式启动
     * 
     * @param embeddedStart 是否嵌入式启动
     */
    public void setEmbeddedStart(boolean embeddedStart) {
        this.embeddedStart = embeddedStart;
    }
}
