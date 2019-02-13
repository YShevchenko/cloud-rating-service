package com.oydipi.ratingservice.controller;

import com.oydipi.ratingservice.model.Rating;
import com.oydipi.ratingservice.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.leader.Context;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {

    private final RatingsRepository ratingsRepository;

    @GetMapping
    public List<Rating> getRatings(@RequestParam(required = false, defaultValue = "0") Integer thresholdStars) {
        log.info("ratings fetched by start more then  {}", thresholdStars);
        List<Rating> ratings = (List<Rating>) ratingsRepository.findAll();
        return ratings.stream().filter(p -> p.getStarsRating() > thresholdStars).collect(toList());
    }


    @GetMapping("/{bookId}")
    public List<Rating> getRating(@PathVariable String bookId) {
        log.info("rating retrieved for book id {}", bookId);
        List<Rating> ratings = (List<Rating>) ratingsRepository.findAll();
        return ratings.stream().filter(p -> bookId.equals(p.getBookId())).collect(toList());
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        log.info("rating added {}", rating);
        return ratingsRepository.save(rating);



    }

}
