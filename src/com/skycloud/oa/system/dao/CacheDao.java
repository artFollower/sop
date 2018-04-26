package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

public interface CacheDao {


/**
 * 添加\更新缓存关键字
 * @author yanyufeng
 * @param key
 * @param userId
 */
public void updateCache(String key,String userId);

/**
 * 得到关键字list
 * @author yanyufeng
 * @param text
 * @param userId
 * @return
 */
public List<Map<String,Object>> getCacheResult(String text,String userId);

}
