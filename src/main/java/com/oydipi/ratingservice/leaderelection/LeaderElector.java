package com.oydipi.ratingservice.leaderelection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Random;

@Service
public class LeaderElector {

    private static final int LOCK_TIMEOUT = 60000;
    private static final String LOCK = "LOCK";
    private Jedis jedis;
    private String myApplicationId;


    public LeaderElector(Jedis jedis, @Value("${spring.cloud.config.name}") String applicationId) {
        this.jedis = jedis;
        this.myApplicationId = applicationId + "-" + new Random().nextInt(Integer.MAX_VALUE);
    }

    @Scheduled(fixedDelay = 10000)
    public void acquireLock() {
        jedis.set(LOCK, "" + myApplicationId, "NX", "PX", LOCK_TIMEOUT);
        if (this.isLeader()) {
            System.out.println("[" + myApplicationId + "] Who's the boss now?!");
        } else {
            System.out.println("[" + myApplicationId + "] It's sad being a non-leader node :-( ");
        }
    }

    public boolean isLeader() {
        return myApplicationId.equals(jedis.get(LOCK));
    }
}