package com.example.livreservice.Model;

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
    public String image;
    public String datePublication;
    public String langue;
    public int nbPages;
    public double prix;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    private String categoryName;


}
