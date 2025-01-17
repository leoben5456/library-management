package com.example.livreservice.Service;


import com.example.livreservice.Model.Livre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {

    void addBookToWishlist(int id, String email);
    List<Livre> getBooksFromWishlist(String email);


}
