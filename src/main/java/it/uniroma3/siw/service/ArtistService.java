package it.uniroma3.siw.service;

import java.io.IOException;

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

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ImageRepository imageRepository;

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

}
