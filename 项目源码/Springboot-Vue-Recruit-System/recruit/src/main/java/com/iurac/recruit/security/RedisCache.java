package com.iurac.recruit.security;

import com.iurac.recruit.entity.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RedisCache<k,v> implements Cache<k,v> {


    private String cacheName;
    private RedisTemplate redisTemplate;

    public RedisCache() {
    }

    public RedisCache(String cacheName,RedisTemplate redisTemplate) {
        this.cacheName = cacheName;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public v get(k k) throws CacheException {
        String s = k.toString();
        if(k instanceof User){
            s = ((User) k).getUsername();
        }
        System.out.println("get==========="+s+"-->("+cacheName+")");
        return (v) redisTemplate.opsForHash().get(this.cacheName,s);
    }

    @Override
    public v put(k k, v v) throws CacheException {
        String s = k.toString();
        if(k instanceof User){
            s = ((User) k).getUsername();
        }
        redisTemplate.opsForHash().put(this.cacheName,s,v);
        System.out.println("put==========="+s+"==========="+v.toString()+"-->("+cacheName+")");
        return v;
    }

    @Override
    public v remove(k k) throws CacheException {
        String s = k.toString();
        if(k instanceof User){
            s = ((User) k).getUsername();
        }
        System.out.println("delete==========="+s+"-->("+cacheName+")");
        return (v) redisTemplate.opsForHash().delete(this.cacheName,s);
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(this.cacheName);
    }

    @Override
    public int size() {
        return redisTemplate.opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        return redisTemplate.opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return redisTemplate.opsForHash().values(this.cacheName);
    }
}
