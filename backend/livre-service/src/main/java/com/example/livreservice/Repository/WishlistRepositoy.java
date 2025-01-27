package com.example.livreservice.Repository;


import com.example.livreservice.Model.Wishlist;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepositoy extends JpaRepository<Wishlist,Integer> {
    Wishlist findByEmail(String email);
    @Modifying

    @Query(value = "DELETE FROM wishlist_livres WHERE livre_id = :livreId", nativeQuery = true)
    void deleteByLivreId(@Param("livreId") int livreId);


}
