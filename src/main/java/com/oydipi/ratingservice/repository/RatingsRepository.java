package com.oydipi.ratingservice.repository;

import com.oydipi.ratingservice.model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends CrudRepository<Rating, String> {
    List<Rating> findRatingsByBookId(String bookId);
}
