package com.epam.brest.course2015.social.dao;

import com.epam.brest.course2015.social.core.SocialToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/**
 * Created by alexander_borohov on 20.7.16.
 */
public interface SecurityDao {

    void addToken(SocialToken token);

    @Caching(
        evict = {
            @CacheEvict(cacheNames = {"token"}, key = "#token.token"),
            @CacheEvict(cacheNames = {"tokenById"}, key = "#token.userId")
        }
    )
    void deleteToken(SocialToken token);

    @Cacheable(value = "userIdByToken", key = "#token.userId")
    Integer getUserId(SocialToken token);

    @Cacheable(value = "token", key = "#token")
    SocialToken getToken(String token);

    @Cacheable(value = "tokenById", key = "#userId", unless = "#result == null")
    SocialToken getTokenByUserId(Integer userId);

}
