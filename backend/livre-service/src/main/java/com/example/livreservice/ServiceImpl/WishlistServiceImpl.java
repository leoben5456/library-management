package com.example.livreservice.ServiceImpl;


import com.example.livreservice.Model.Livre;
import com.example.livreservice.Model.Wishlist;
import com.example.livreservice.Repository.LivreRepository;
import com.example.livreservice.Repository.WishlistRepositoy;
import com.example.livreservice.Service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepositoy wishlistRepositoy;

    private  final LivreRepository livreRepository;

    public WishlistServiceImpl(WishlistRepositoy wishlistRepositoy, LivreRepository livreRepository) {
        this.wishlistRepositoy = wishlistRepositoy;
        this.livreRepository = livreRepository;
    }


    @Override
    public void addBookToWishlist(int Livreid, String email) {
        Wishlist wishlist = wishlistRepositoy.findByEmail(email);
        if(wishlist==null){
            wishlist = new Wishlist();
            wishlist.setEmail(email);
        }

        Optional<Livre> livre = livreRepository.findById(Livreid);
        if(livre.isPresent()){
            wishlist.getLivres().add(livre.get());
            wishlistRepositoy.save(wishlist);
        }

    }

    @Override
    public List<Livre> getBooksFromWishlist(String email) {

        Wishlist wishlist = wishlistRepositoy.findByEmail(email);


        if (wishlist == null) {
            return Collections.emptyList();
        }


        return wishlist.getLivres();
    }


}
