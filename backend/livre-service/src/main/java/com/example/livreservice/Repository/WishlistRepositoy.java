package com.example.livreservice.Repository;


import com.example.livreservice.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepositoy extends JpaRepository<Wishlist,Integer> {
    Wishlist findByEmail(String email);
}
