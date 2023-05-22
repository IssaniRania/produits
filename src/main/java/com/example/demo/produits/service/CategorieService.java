package com.example.demo.produits.service;

import com.example.demo.produits.entities.Categorie;
import com.example.demo.produits.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategorieService {
    Categorie saveCategorie(Categorie c);

    Categorie updateCategorie(Categorie c);
    void deleteCategorie(Categorie c);
    void deleteCategorieById(Long id);
    Categorie getCategorie(Long id);
    List<Categorie> getAllCategories();
    Page<Categorie> getAllCategoriesParPage(int page, int size);
   Categorie getCategorieById(Long id);
    //List<Categorie> findByNomCategorieContains(String nom);
   //List<Categorie> findByNomCategorie(String nom);
    //List<Categorie> findByDescription(String description);*/
    List<Categorie> findByNomCatContains(String nom);
}
