package it.uniroma3.siw.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import it.uniroma3.siw.repository.UserRepository;

@Controller
public class ReviewController {

  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private MovieRepository movieRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionData sessionData;

  @Transactional
  @PostMapping("/review")
  public String newReview(Model model, @RequestParam("movie") Long movieId, @RequestParam("rating") int rating,
      @RequestParam("title") String title, @RequestParam("comment") String comment) {
    Movie movie = this.movieRepository.findById(movieId).get();
    User user = this.sessionData.getLoggedUser();
    if (!user.getReviews().containsKey(movie)) {
      Review review = new Review();
      review.setRating(rating);
      review.setTitle(title);
      review.setComment(comment);
      review.setUser(user);
      review.setMovie(movie);
      this.reviewRepository.save(review);

      user.getReviews().put(movie, review);
      movie.getReviews().add(review);

      this.userRepository.save(user);
      this.movieRepository.save(movie);
    }
    
    model.addAttribute("userReview", user.getReviews().get(movie));
    model.addAttribute("user", user);
    model.addAttribute("movie", movie);
    model.addAttribute("movieReviews", this.reviewRepository.findMovieReviewsWithoutUser(movieId, user.getId()));
    return "movie.html";

  }

}
