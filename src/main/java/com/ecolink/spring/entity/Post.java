package com.ecolink.spring.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Startup startup;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "post_ods", joinColumns = @JoinColumn(name = "id_post"), inverseJoinColumns = @JoinColumn(name = "id_ods"))
    private List<Ods> odsLists = new ArrayList<>(); 

    private String title;
    private String description;
    private LocalDate postDate;

    public Post(Startup startup, List<Ods> ods, String title, String description, LocalDate postDate) {
        this.startup = startup;
        this.odsLists = ods;
        this.title = title;
        this.description = description;
        this.postDate = postDate;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    //Metodos helpers LIKE
    public void addLike(Like like) {
        this.likes.add(like);
    }

    //Post
    public void addOds(Ods ods){
        this.odsLists.add(ods);
        ods.addPost(this);
    }

    public void removeOds(Ods ods){
        this.odsLists.remove(ods);
        ods.removePost(this);
        
    }
}
