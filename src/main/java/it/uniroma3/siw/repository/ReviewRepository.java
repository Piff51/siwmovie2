package it.uniroma3.siw.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query(value = "select * from review as r where r.movie_id = :movieId and r.user_id <> :userId",nativeQuery = true)
	public Iterable<Review> findMovieReviewsWithoutUser(@Param("movieId") Long movieId, @Param("userId") Long userId);
}