package it.uniroma3.siw.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name;
	private String surname;
	private String email;

	@OneToMany(mappedBy = "user")
	private Map<Movie,Review> reviews;

	public User(){
		this.reviews = new HashMap<>();
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Map<Movie,Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Map<Movie,Review> reviews) {
		this.reviews = reviews;
	}

}