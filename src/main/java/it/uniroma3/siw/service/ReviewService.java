package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public Iterable<Review> findMovieReviewsWithoutUser(Long movieId, Long userId) {
        return this.reviewRepository.findMovieReviewsWithoutUser(movieId, userId);
    }
    
}
