package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void saveArtist(@Valid Artist artist, MultipartFile image) throws IOException{
        Image artistImg = new Image(image.getBytes());
        this.imageRepository.save(artistImg);
        artist.setProfilePicture(artistImg);
        this.artistRepository.save(artist);
    }
    @Transactional
    public Artist findArtist(Long id) {
        return this.artistRepository.findById(id).get();
    }
    @Transactional
    public Iterable<Artist> findAllArtists() {
        return this.artistRepository.findAll();
    }
    @Transactional
    public Iterable<Artist> findActorsNotInMovie(Long movieId) {
        return this.artistRepository.findActorsNotInMovie(movieId);
    }
    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = this.findArtist(id);
        for (Movie movie : artist.getStarredMovies()) {
            movie.getActors().remove(artist);
        }
        for (Movie movie : artist.getDirectorOf()){
            movie.setDirector(null);
        }
        this.artistRepository.delete(artist);
    }
    @Transactional
    public List<Movie> directedMovieToAdd(Long id) {
        List<Movie> moviesToAdd = new ArrayList<>();

		for (Movie m : this.movieService.findMovieNotDirected(id)) {
			moviesToAdd.add(m);
		}
		return moviesToAdd;
    }
  
    public List<Movie> starredMovieToAdd(Long id) {
        List<Movie> moviesToAdd = new ArrayList<>();

		for (Movie m : this.movieService.findMovieNotStarred(id)) {
			moviesToAdd.add(m);
		}
		return moviesToAdd;
    }
    public Artist addDirectedMovieToArtist(Long movieId, Long artistId) {
        Artist artist = this.findArtist(artistId);
        Movie movie = this.movieService.findMovie(movieId);
        artist.getDirectedMovies().add(movie);
        movie.setDirector(artist);
        this.movieRepository.save(movie);
        return artist;
    }
    @Transactional
    public Artist removeDirectedMovieFromArtist(Long artistId, Long movieId) {
        Movie movie = this.movieService.findMovie(movieId);
        Artist artist = this.findArtist(artistId);
        artist.getDirectedMovies().remove(movie);
        movie.setDirector(null);
        this.movieRepository.save(movie);
        return artist;
    }
    public Artist addStarredMovieToArtist(Long movieId, Long artistId) {
        Artist artist = this.findArtist(artistId);
        Movie movie = this.movieService.findMovie(movieId);
        artist.getStarredMovies().add(movie);
        movie.getActors().add(artist);
        this.artistRepository.save(artist);
        return artist;
    }
   
    @Transactional
    public Artist removeStarredMovieFromArtist(Long artistId, Long movieId) {
        Movie movie = this.movieService.findMovie(movieId);
        Artist artist = this.findArtist(artistId);
        artist.getStarredMovies().remove(movie);
        movie.getActors().remove(artist);
        this.artistRepository.save(artist);
        return artist;
    }

}
