package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

	public List<Movie> findByYear(int year);

	public boolean existsByTitleAndYear(String title, int year);

    public List<Movie> findByTitle(String title);


	@Query(value = "select * from movie m where m.director_id is null", nativeQuery = true)
    public Iterable<Movie> findMovieNotDirected(@Param("artistId") Long id);

	@Query(value = "select * from movie m where m.id not in (select starred_movies_id from movie_actors ma where ma.actors_id = :artistId)", nativeQuery = true)
    public Iterable<Movie> findMovieNotStarred(@Param("artistId") Long id);	

	@Query(value = "select * from movie m where m.director_id = :artistId", nativeQuery = true)
    public Iterable<Movie> findDirectedMovies(@Param("artistId")Long id);
}