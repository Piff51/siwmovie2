package it.uniroma3.siw.model;


import java.util.Base64;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    public Image(){

    }

    public Image(byte[] bytes){
        this.image = bytes;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getbase64Image(){
        return Base64.getEncoder().encodeToString(image);
    }
   
}
