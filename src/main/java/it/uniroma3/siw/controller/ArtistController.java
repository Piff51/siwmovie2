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
import it.uniroma3.siw.controller.validator.ArtistValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.service.ArtistService;

@Controller
public class ArtistController {

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private ArtistService artistService;

	
	@GetMapping(value = "/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist.html";
	}

	
	@GetMapping(value = "/admin/indexArtist")
	public String indexArtist() {
		return "admin/indexArtist.html";
	}

	
	@PostMapping("/admin/artist")
	public String newArtist(Model model, @Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult,
			@RequestParam("file") MultipartFile image) throws IOException {
		this.artistValidator.validate(artist, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.artistService.saveArtist(artist, image);
			model.addAttribute("artist", artist);
			return "artist.html";
		} else {
			return "admin/formNewArtist.html";
		}
	}

	
	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findArtist(id));
		model.addAttribute("directedMovies", this.artistService.findDirectedMovies(id));
		return "artist.html";
	}

	
	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "artists.html";
	}

	
	@GetMapping(value = "/admin/manageArtists")
	public String manageArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "admin/manageArtists.html";
	}

	
	@GetMapping(value = "/admin/DeleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id, Model model) {
		this.artistService.deleteArtist(id);
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "admin/manageArtists.html";
	}

	
	@GetMapping(value = "/admin/formUpdateArtist/{id}")
	public String formUpdateArtist(@PathVariable("id") Long id, Model model) {
		Artist artist = this.artistService.findArtist(id);
		List<Movie> directedMovies = this.artistService.getDirectedMovies(id);
		model.addAttribute("artist", artist);
		model.addAttribute("directedMovies", directedMovies);
		return "admin/formUpdateArtist.html";
	}

	
	@GetMapping("/admin/updateDirectedMovies/{id}")
	public String updateDirectedMovies(@PathVariable("id") Long id, Model model) {
		List<Movie> movieToAdd = this.artistService.directedMovieToAdd(id);
		Artist artist = this.artistService.findArtist(id);
		model.addAttribute("directedMoviesToAdd", movieToAdd);
		model.addAttribute("directedMovies", this.artistService.getDirectedMovies(id));
		model.addAttribute("artist",artist);
		return "admin/directedMoviesToAdd.html";
	}

	
	@GetMapping("/admin/updateStarredMovies/{id}")
	public String updateStarredMovies(@PathVariable("id") Long id, Model model) {
		List<Movie> movieToAdd = this.artistService.starredMovieToAdd(id);
		model.addAttribute("starredMoviesToAdd", movieToAdd);
		model.addAttribute("artist", this.artistService.findArtist(id));
		return "admin/starredMoviesToAdd.html";
	}

	
	@GetMapping(value = "/admin/addDirectedMovieToArtist/{artistId}/{movieId}")
	public String addDirectedMovieToArtist(@PathVariable("artistId") Long artistId,
			@PathVariable("movieId") Long movieId,
			Model model) {
		Artist artist = this.artistService.addDirectedMovieToArtist(movieId, artistId);
		List<Movie> moviesToAdd = this.artistService.directedMovieToAdd(artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("directedMovies", this.artistService.getDirectedMovies(artistId));
		model.addAttribute("directedMoviesToAdd", moviesToAdd);
		return "admin/directedMoviesToAdd.html";
	}

	
	@GetMapping(value = "/admin/addStarredMovieToArtist/{artistId}/{movieId}")
	public String addStarredMovieToArtist(@PathVariable("artistId") Long artistId,
			@PathVariable("movieId") Long movieId,
			Model model) {
		Artist artist = this.artistService.addStarredMovieToArtist(movieId, artistId);
		List<Movie> moviesToAdd = this.artistService.starredMovieToAdd(artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("starredMoviesToAdd", moviesToAdd);
		return "admin/starredMoviesToAdd.html";
	}

	
	@GetMapping(value = "/admin/removeDirectedMovieFromArtist/{artistId}/{movieId}")
	public String removeDirectedMovieFromArtist(@PathVariable("artistId") Long artistId,
			@PathVariable("movieId") Long movieId,
			Model model) {
		Artist artist = this.artistService.removeDirectedMovieFromArtist(artistId, movieId);
		List<Movie> moviesToAdd = this.artistService.directedMovieToAdd(artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("directedMovies", this.artistService.getDirectedMovies(artistId));
		model.addAttribute("directedMoviesToAdd", moviesToAdd);
		return "admin/directedMoviesToAdd.html";
	}

	
	@GetMapping(value = "/admin/removeStarredMovieFromArtist/{artistId}/{movieId}")
	public String removeStarredMovieFromArtist(@PathVariable("artistId") Long artistId,
			@PathVariable("movieId") Long movieId,
			Model model) {
		Artist artist = this.artistService.removeStarredMovieFromArtist(artistId, movieId);
		List<Movie> moviesToAdd = this.artistService.starredMovieToAdd(artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("starredMoviesToAdd", moviesToAdd);
		return "admin/starredMoviesToAdd.html";
	}
	
}
