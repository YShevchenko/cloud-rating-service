package com.oydipi.ratingservice.batch;

import com.oydipi.ratingservice.leaderelection.LeaderElector;
import com.oydipi.ratingservice.model.Rating;
import com.oydipi.ratingservice.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.leader.Context;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Log4j2
public class BatchJobReport {
//
//    private final LeaderElector leaderElector;
//    private final RatingsRepository ratingsRepository;

    private final LockRegistryLeaderInitiator lockRegistryLeaderInitiator;


    @Scheduled(fixedDelay = 2000)
    public void showAverageRatingBatch() {
//        leaderElector.acquireLock();
//        if (leaderElector.isLeader()) {
//            List<Rating> ratings = (List<Rating>) ratingsRepository.findAll();
//            log.info("ratings: {}", ratings.stream().collect(groupingBy(Rating::getBookId, averagingDouble(Rating::getStarsRating))));
//        }


        if (lockRegistryLeaderInitiator.getContext().isLeader()) {
            System.out.println("Leader");
        } else {
            System.out.println("Not leader");
        }
    }
}
