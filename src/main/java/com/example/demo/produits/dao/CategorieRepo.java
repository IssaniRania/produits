package com.example.demo.produits.dao;

import com.example.demo.produits.entities.Categorie;
import com.example.demo.produits.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorieRepo extends JpaRepository<Categorie,Long> {
    List<Categorie> findByNomCat(String nomCat);
    List<Categorie> findByNomCatContaining(String nomCat);
    @Query("select c from Categorie c where c.descriptionCat like %:descriptionCat")
         List<Categorie> findByDescriptionCatContaining(@Param("descriptionCat") String descriptionCat);
    List<Categorie> findByProduitsNomProduit(String nomProduit);
    List<Categorie> findByProduitsNomProduitContaining(String nomProduit);
    List<Categorie> findByOrderByNomCatAsc();
    @Query("select c from Categorie c order by c.nomCat ASC, c.descriptionCat ASC")
    List<Categorie> trierCategoriesNomsDescriptions();
}
