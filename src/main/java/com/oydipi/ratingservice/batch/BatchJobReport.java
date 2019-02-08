package com.oydipi.ratingservice.batch;

import com.oydipi.ratingservice.leaderelection.LeaderElector;
import com.oydipi.ratingservice.model.Rating;
import com.oydipi.ratingservice.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Log4j2
public class BatchJobReport {

    private final LeaderElector leaderElector;
    private final RatingsRepository ratingsRepository;

    @Scheduled(fixedDelay = 20000)
    public void showAverageRatingBatch() {
        leaderElector.acquireLock();
        if (leaderElector.isLeader()) {
            List<Rating> ratings = (List<Rating>) ratingsRepository.findAll();
            log.info("ratings: {}", ratings.stream().collect(groupingBy(Rating::getBookId, averagingDouble(Rating::getStarsRating))));
        }
    }
}
