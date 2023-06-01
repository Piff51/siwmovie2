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

import it.uniroma3.siw.controller.validator.ImageValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageValidator imageValidator;

    @Transactional
    public Movie findMovie(Long id) {
        return movieRepository.findById(id).get();
    }

    @Transactional
    public Iterable<Movie> findAllMovies() {
        return this.movieRepository.findAll();
    }

    @Transactional
    public Movie setDirectorToMovie(Long directorId, Long movieId) {
        Artist director = this.artistService.findArtist(directorId);
        Movie movie = this.findMovie(movieId);
        movie.setDirector(director);
        this.movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public void saveMovie(@Valid Movie movie, MultipartFile image) throws IOException {
        Image movieImg = new Image(image.getBytes());
        this.imageRepository.save(movieImg);

        movie.setImage(movieImg);
        this.movieRepository.save(movie);
    }

    @Transactional
    public Iterable<Movie> findMovieByYear(int year) {
        return this.movieRepository.findByYear(year);
    }

    @Transactional
    public List<Artist> actorsToAdd(Long movieId) {
        List<Artist> actorsToAdd = new ArrayList<>();

        for (Artist a : this.artistService.findActorsNotInMovie(movieId)) {
            actorsToAdd.add(a);
        }
        return actorsToAdd;
    }

    @Transactional
    public Movie addActorToMovie(Long movieId, Long actorId) {
        Movie movie = this.findMovie(movieId);
        Artist actor = this.artistService.findArtist(actorId);
        Set<Artist> actors = movie.getActors();
        actors.add(actor);
        this.movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public Movie removeActorFromMovie(Long actorId, Long movieId) {
        Movie movie = this.findMovie(movieId);
        Artist actor = this.artistService.findArtist(actorId);
        Set<Artist> actors = movie.getActors();
        actors.remove(actor);
        this.movieRepository.save(movie);
        return movie;
    }

    public List<Movie> findMovieByTitle(String title) {
        return this.movieRepository.findByTitle(title);
    }

    public void deleteMovie(Long id) {
        Movie movie = this.findMovie(id);
        for (Artist artist : movie.getActors()) {
            artist.getStarredMovies().remove(movie);
        }
        if(movie.getDirector()!=null){
            movie.getDirector().getDirectedMovies().remove(movie);
        }
        this.movieRepository.delete(movie);
    }

    @Transactional
    public Iterable<Movie> findMovieNotDirected(Long id) {
        return this.movieRepository.findMovieNotDirected(id);
    }

    @Transactional
    public Iterable<Movie> findMovieNotStarred(Long id) {
        return this.movieRepository.findMovieNotStarred(id);
    }

    public void addImage(Movie movie, MultipartFile image) throws IOException{
        if (this.imageValidator.isImage(image) || image.getSize() < ImageValidator.MAX_IMAGE_SIZE){
            Image movieImg = new Image(image.getBytes());
            this.imageRepository.save(movieImg);

            movie.getImages().add(movieImg);
            this.movieRepository.save(movie);
        }
    }

    public void removeImage(Long movieId, Long imageId) {
        Image image = this.imageRepository.findById(imageId).get();
        Movie movie = this.findMovie(movieId);
        movie.getImages().remove(image);
        this.movieRepository.save(movie);
    }
    
}

    


