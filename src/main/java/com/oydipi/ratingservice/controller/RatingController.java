package com.oydipi.ratingservice.controller;

import com.oydipi.ratingservice.model.Rating;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
@Log4j2
public class RatingController {

    //setup some data for test
    private static List<Rating> ratings = new ArrayList<>(
            Arrays.asList(
                    new Rating("r1", "b2", 2),
                    new Rating("r2", "b1", 3))

    );

    @GetMapping
    public List<Rating> getRatings(@RequestParam(required = false, defaultValue = "0") Integer thresholdStars) {
        log.info("ratings fetched by start more then  {}", thresholdStars);
        return thresholdStars == null ? ratings : ratings.stream().filter(p -> p.getStarsRating() > thresholdStars).collect(Collectors.toList());
    }


    @GetMapping("/{bookId}")
    public Rating getRating(@PathVariable String bookId) {
        log.info("rating retrieved for book id {}", bookId);
        return ratings.stream().filter(r -> bookId.equals(r.getBookId())).findFirst().orElse(null);
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        log.info("rating added {}", rating);
        return ratings.add(rating) ? rating : null;
    }
}
