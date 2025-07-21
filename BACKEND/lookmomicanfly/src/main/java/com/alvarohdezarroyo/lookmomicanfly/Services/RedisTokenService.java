package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Service
public class RedisTokenService {

    private final Duration tokenLength;
    private final String tokenPrefix;
    private final String userTokenPrefix;

    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    public RedisTokenService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        tokenLength = Duration.ofMillis(AppConfig.getTokenDuration()); // Long.valueOf(AppConfig.getTokenDuration())
        tokenPrefix = AppConfig.getJwtTokenPrefix();
        userTokenPrefix = AppConfig.getUserTokensPrefixValue();
    }

    public void save(String token, String userId, String ip, String device) {
        stringRedisTemplate.opsForHash()
                .putAll(tokenPrefix + token,
                        Map.of("userId", userId,
                                "createdAt", Instant.now().toString(),
                                "ip", ip,
                                "device", device,
                                "expiresAt", Instant.now().plusSeconds(tokenLength.toSeconds()).toString()));
        stringRedisTemplate.expire(tokenPrefix + token, tokenLength);
        stringRedisTemplate.opsForSet().add(userTokenPrefix + userId, tokenPrefix + token);
    }

    public boolean checkIfTokenIsValid(String token) {
        Boolean exists = stringRedisTemplate.hasKey(tokenPrefix + token); // needs to be primitive in order to check if it's null
        if (!exists) {
            return false;
        }
        final String expiresAt = (String) stringRedisTemplate.opsForHash().get(tokenPrefix + token, "expiresAt");
        if (expiresAt == null) {
            return false;
        }
        return Instant.parse(expiresAt).isAfter(Instant.now());
    }

    public void getAllTokensByUserId(String userId) {

    }

}
