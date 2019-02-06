package com.oydipi.ratingservice.controller;

import com.oydipi.ratingservice.model.Rating;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    //setup some data for test
    private static List<Rating> ratings = new ArrayList<>(
            Arrays.asList(
                    new Rating("r1", "b2", 2),
                    new Rating("r2", "b1", 3))

    );

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratings;
    }

    @GetMapping("/{bookId}")
    public Rating getRating(@PathVariable String bookId) {
        return ratings.stream().filter(r -> bookId.equals(r.getBookId())).findFirst().orElse(null);
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratings.add(rating) ? rating : null;
    }
}
