package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public Iterable<Review> findMovieReviewsWithoutUser(Long movieId, Long userId) {
        return this.reviewRepository.findMovieReviewsWithoutUser(movieId, userId);
    }

    public Review findReview(Long id) {
        return this.reviewRepository.findById(id).get();
    }

    public boolean checkUserReview(User user, Long reviewId) {
        Review review = this.findReview(reviewId);
        if (user.equals(review.getUser())) { // controlla se la review l'ha effettivamente scritta
                                                           // l'utente che l'ha creata
            return true;
        } else {
            return false;
        }
    }

    public void removeReview(Long reviewId) {
        Review review = this.findReview(reviewId);
        Movie movie = review.getMovie();
        User user = review.getUser();
        movie.getReviews().remove(review);
        user.getReviews().remove(movie);
        this.reviewRepository.deleteById(reviewId);
    }

    public void save(Review review) {
        this.reviewRepository.save(review);
    }

}
