package com.example.livreservice.DTO;

import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivreDTO {
    private int id;
    private String titre;
    private String auteur;
    private String genre;
    private String image;
    private String datePublication;
    private String langue;
    private int nbPages;
    private double prix;
    private String categoryName;
    private Status status;



    // Helper method to check availability
    public boolean isDisponible() {
        return status == Status.AVAILABLE ||
                status == Status.BORROWED;
    }
}