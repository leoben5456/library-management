package com.example.livreservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String titre;
    public String auteur;
    public String genre;
    public String image="";
    public String datePublication="";
    public String langue;
    private Boolean isReserved=false;
    public Integer quantite;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @Transient
    private String categoryName;
    @Enumerated(EnumType.STRING)
    private Status status=Status.AVAILABLE;
    private String coverPath;
    @Lob
    private String description;
    private float rating;

}
