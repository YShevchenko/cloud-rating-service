package com.oydipi.ratingservice.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;

@Configuration
public class ZookeeperConfig {


    @Bean
    public LeaderInitiatorFactoryBean leaderInitiator(CuratorFramework client) {
        return new LeaderInitiatorFactoryBean()
                .setClient(client)
                .setPath("/siTest/")
                .setRole("cluster");
    }

    @Bean
    public LockRegistryLeaderInitiator lockRegistryLeaderInitiator(LockRegistry locks) {
        return new LockRegistryLeaderInitiator(locks);
    }

    @Bean
    public LockRegistry lockRegistry() {
        return new DefaultLockRegistry();
    }

    @Bean
    public CuratorFramework curatorClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder().defaultData(new byte[0])
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectString("localhost:2181").build();
        client.start();
        return client;
    }
}
