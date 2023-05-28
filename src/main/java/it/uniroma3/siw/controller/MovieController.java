package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class MovieController {
	@Autowired
	private MovieValidator movieValidator;
	@Autowired
	private SessionData sessionData;
	@Autowired
	private MovieService movieService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private ReviewService reviewService;

	@Transactional
	@GetMapping(value = "/admin/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "admin/formNewMovie.html";
	}

	@Transactional
	@GetMapping(value = "/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		Movie movie = this.movieService.findMovie(id);
		model.addAttribute("movie", movie);
		return "admin/formUpdateMovie.html";
	}

	@Transactional
	@GetMapping(value = "/admin/DeleteMovie/{id}")
	public String deleteMovie(@PathVariable("id") Long id, Model model) {
		this.movieService.deleteMovie(id);
		model.addAttribute("movies", this.movieService.findAllMovies());
		return "admin/manageMovies.html";
	}

	@Transactional
	@GetMapping(value = "/admin/indexMovie")
	public String indexMovie() {
		return "admin/indexMovie.html";
	}

	@Transactional
	@GetMapping(value = "/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieService.findAllMovies());
		return "admin/manageMovies.html";
	}

	@Transactional
	@GetMapping(value = "/admin/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId,
			Model model) {
		Movie movie = this.movieService.setDirectorToMovie(directorId, movieId);
		model.addAttribute("movie", movie);
		return "admin/formUpdateMovie.html";
	}

	@Transactional
	@GetMapping(value = "/admin/addDirector/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artists", this.artistService.findAllArtists());
		model.addAttribute("movie", this.movieService.findMovie(id));
		return "admin/directorsToAdd.html";
	}

	@Transactional
	@PostMapping("/admin/movie")
	public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult,
			@RequestParam("file") MultipartFile image) throws IOException {
		this.movieValidator.validate(movie, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.movieService.saveMovie(movie, image);
			model.addAttribute("movie", movie);
			model.addAttribute("base64Image", movie.getImage().getbase64Image());
			return "movie.html";
		} else {
			return "admin/formNewMovie.html";
		}
	}

	@Transactional
	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		Movie movie = this.movieService.findMovie(id);
		model.addAttribute("movie", movie);
		try {
			User user = this.sessionData.getLoggedUser();
			model.addAttribute("user", user);
			if (user.getReviews().containsKey(movie)) {
				model.addAttribute("userReview", user.getReviews().get(movie));
			}
			model.addAttribute("movieReviews",
					this.reviewService.findMovieReviewsWithoutUser(movie.getId(), user.getId()));
			return "movie.html";
		} catch (Exception e) {
			model.addAttribute("movieReviews", movie.getReviews());
			return "movie.html";
		}
	}

	@Transactional
	@GetMapping("/movie")
	public String getMovies(Model model) {
		model.addAttribute("movies", this.movieService.findAllMovies());
		return "movies.html";
	}

	@Transactional
	@GetMapping("/formSearchMovies")
	public String formSearchMovies() {
		return "formSearchMovies.html";
	}

	@Transactional
	@PostMapping("/searchMovies")
	public String searchMovies(Model model, @RequestParam int year) {
		model.addAttribute("movies", this.movieService.findMovieByYear(year));
		return "foundMovies.html";
	}

	@Transactional
	@PostMapping("/searchMoviesByTitle")
	public String searchMoviesByTitle(Model model, @RequestParam String title) {
		model.addAttribute("movies", this.movieService.findMovieByTitle(title));
		return "foundMovies.html";
	}

	@Transactional
	@GetMapping("/admin/updateActors/{id}")
	public String updateActors(@PathVariable("id") Long id, Model model) {
		List<Artist> actorsToAdd = this.movieService.actorsToAdd(id);
		model.addAttribute("actorsToAdd", actorsToAdd);
		model.addAttribute("movie", this.movieService.findMovie(id));
		return "admin/actorsToAdd.html";
	}

	@Transactional
	@GetMapping(value = "/admin/addActorToMovie/{actorId}/{movieId}")
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId,
			Model model) {
		Movie movie = this.movieService.addActorToMovie(movieId, actorId);
		List<Artist> actorsToAdd = this.movieService.actorsToAdd(movieId);
		model.addAttribute("movie", movie);
		model.addAttribute("actorsToAdd", actorsToAdd);
		return "admin/actorsToAdd.html";
	}

	@Transactional
	@GetMapping(value = "/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId,
			Model model) {
		Movie movie = this.movieService.removeActorFromMovie(actorId, movieId);
		List<Artist> actorsToAdd = this.movieService.actorsToAdd(movieId);
		model.addAttribute("movie", movie);
		model.addAttribute("actorsToAdd", actorsToAdd);
		return "admin/actorsToAdd.html";
	}
}