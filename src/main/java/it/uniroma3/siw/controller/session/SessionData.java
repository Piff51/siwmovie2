package it.uniroma3.siw.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    @Autowired
    private CredentialsRepository credentialsRepository;

    public Credentials getLoggedCredentials() {
        UserDetails loggedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
    }
    
    public User getLoggedUser() {
        
        return this.getLoggedCredentials().getUser();
    }
}