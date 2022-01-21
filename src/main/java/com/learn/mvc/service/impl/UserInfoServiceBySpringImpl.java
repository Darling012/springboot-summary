package com.learn.mvc.service.impl;

import com.learn.mvc.controller.pojo.UserInfo;
import com.learn.mvc.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Slf4j
@Service("userInfoServiceBySpring")
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserInfoServiceBySpringImpl implements UserInfoService {

    /**
     * 模拟数据库存储数据
     */
    private HashMap<Integer, UserInfo> userInfoMap = new HashMap<>();

    @Override
    @CachePut(value = "userInfo", key = "#userInfo.id")
    public UserInfo addUserInfo(UserInfo userInfo) {
        log.info("create");
        userInfoMap.put(userInfo.getId(), userInfo);
         return userInfoMap.get(userInfo.getId());
    }

    /**
     * Cacheable
     * value：缓存key的前缀。
     * key：缓存key的后缀。
     * sync：设置如果缓存过期是不是只放一个请求去请求数据库，其他请求阻塞，默认是false。
     */
    @Override
    @Cacheable(value = "userInfo",key = "#id",sync = true)
    public UserInfo getById(Integer id) {
        log.info("get");
        return userInfoMap.get(id);
    }

    @Override
    @CachePut(key = "#userInfo.id")
    public UserInfo updateUserInfo(UserInfo userInfo) {
        log.info("update");
        if (!userInfoMap.containsKey(userInfo.getId())) {
            return null;
        }
        // 取旧的值
        UserInfo oldUserInfo = userInfoMap.get(userInfo.getId());
        // 替换内容
        if (!StringUtils.isEmpty(oldUserInfo.getAge())) {
            oldUserInfo.setAge(userInfo.getAge());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getName())) {
            oldUserInfo.setName(userInfo.getName());
        }
        if (!StringUtils.isEmpty(oldUserInfo.getSex())) {
            oldUserInfo.setSex(userInfo.getSex());
        }
        // 将新的对象存储，更新旧对象信息
        userInfoMap.put(oldUserInfo.getId(), oldUserInfo);
        // 返回新对象信息
        return oldUserInfo;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {
        log.info("delete");
        userInfoMap.remove(id);
    }

}
