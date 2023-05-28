package it.uniroma3.siw.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private MovieRepository movieRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ReviewService reviewService;
  @Autowired
  private SessionData sessionData;
  @Autowired
  private MovieService movieService;

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
    model.addAttribute("movie", movie);
    model.addAttribute("movieReviews", this.reviewRepository.findMovieReviewsWithoutUser(movieId, user.getId()));
    return "movie.html";

  }

  @Transactional
  @GetMapping(value = "deleteReview/{reviewId}")
  public String removeReview(@PathVariable("reviewId") Long reviewId,Model model){
    User user = this.sessionData.getLoggedUser();
    Movie movie =this.reviewService.findReview(reviewId).getMovie();
    if(this.reviewService.checkUserReview(user, reviewId)){
      this.reviewService.removeReview(reviewId);
      model.addAttribute("movie", movie);
      model.addAttribute("movieReviews", movie.getReviews());
      return "movie.html";
    }
    else{
      return "failedReviewDelete.html";
    }
    
  }
  @Transactional
  @GetMapping(value = "updateReview/{reviewId}")
  public String updateReview(@PathVariable("reviewId") Long reviewId,Model model){
    User user = this.sessionData.getLoggedUser();
    if(this.reviewService.checkUserReview(user, reviewId)){
      Review review = this.reviewService.findReview(reviewId);
      model.addAttribute("movie", review.getMovie());
      model.addAttribute("userReview", review);
     return "formUpdateReview.html";
    }
    else{
      return "failedUpdateDelete.html";
    }
    
  }
  @Transactional
  @PostMapping(value = "updateReview")
  public String updateReview(@RequestParam("review") Long reviewId, @RequestParam("rating")int rating, @RequestParam("title")String title, @RequestParam("comment")String comment, Model model){
    Review review = this.reviewService.findReview(reviewId);
    review.setComment(comment);
    review.setRating(rating);
    review.setTitle(title);
    this.reviewService.save(review);
    User user = this.sessionData.getLoggedUser();
    Movie movie = review.getMovie();
    model.addAttribute("userReview", user.getReviews().get(movie));
    model.addAttribute("movie", movie);
    model.addAttribute("movieReviews", this.reviewRepository.findMovieReviewsWithoutUser(movie.getId(), user.getId()));
    return "movie.html";
  }


  @Transactional
  @GetMapping(value = "admin/deleteReview/{reviewId}")
  public String adminRemoveReview(@PathVariable("reviewId") Long reviewId,Model model){
    Movie movie = this.reviewService.findReview(reviewId).getMovie();
    this.reviewService.removeReview(reviewId);
    model.addAttribute("movie", movie);
    return "admin/formUpdateMovie.html";
    
  }

}
