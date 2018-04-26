package com.skycloud.oa.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import com.skycloud.oa.shiro.JedisManager;

/**
 * 这里的name是指自定义relm中的授权/认证的类名加上授权/认证英文名字
 *
 * @author michael
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

	@Autowired
    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, jedisManager);
    }

    @Override
    public void destroy() {
    	jedisManager.getJedis().shutdown();
    }
}
