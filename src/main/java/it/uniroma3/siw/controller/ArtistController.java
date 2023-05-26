package it.uniroma3.siw.controller;

import java.io.IOException;
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
import it.uniroma3.siw.service.ArtistService;

@Controller
public class ArtistController {
	

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private ArtistService artistService;

	@Transactional
	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist.html";
	}
	
	@Transactional
	@GetMapping(value="/admin/indexArtist")
	public String indexArtist() {
		return "admin/indexArtist.html";
	}
	
	@Transactional
	@PostMapping("/admin/artist")
	public String newArtist(Model model,@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @RequestParam("file") MultipartFile image)  throws IOException{
		this.artistValidator.validate(artist, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.artistService.saveArtist(artist, image);
			model.addAttribute("artist", artist);
			return "artist.html";
		} else {
			return "admin/formNewArtist.html"; 
		}
	}

	@Transactional
	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findArtist(id));
		return "artist.html";
	}

	@Transactional
	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "artists.html";
	}
	@Transactional
	@GetMapping(value = "/admin/manageArtists")
	public String manageArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "admin/manageArtists.html";
	}
	@Transactional
	@GetMapping(value = "/admin/DeleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id, Model model) {
		this.artistService.deleteArtist(id);
		model.addAttribute("artists", this.artistService.findAllArtists());
		return "admin/manageArtists.html";
	}
	@Transactional
	@GetMapping(value = "/admin/formUpdateArtist/{id}")
	public String formUpdateArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.findArtist(id));
		return "admin/formUpdateArtist.html";
	}
}
