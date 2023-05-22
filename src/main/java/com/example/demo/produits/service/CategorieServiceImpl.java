package com.example.demo.produits.service;

import com.example.demo.produits.dao.CategorieRepo;
import com.example.demo.produits.entities.Categorie;
import com.example.demo.produits.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements CategorieService{
    @Autowired
    CategorieRepo categorieRepo;
    @Override
    public Categorie saveCategorie(Categorie c) {
        return categorieRepo.save(c);
    }

    @Override
    public Categorie updateCategorie(Categorie c) {
        return categorieRepo.save(c);
    }
    @Override
    public void deleteCategorie(Categorie c) {
        categorieRepo.delete(c);

    }
    @Override
    public void deleteCategorieById(Long id) {
        categorieRepo.deleteById(id);
    }
    @Override
    public Categorie getCategorie(Long id) {
        return categorieRepo.findById(id).get();
    }
    @Override
    public List<Categorie> getAllCategories() {

        return categorieRepo.findAll();
    }
    @Override
    public Page<Categorie> getAllCategoriesParPage(int page, int size) {
        // TODO Auto-generated method stub
        return categorieRepo.findAll(PageRequest.of(page, size));
    }
   @Override
    public Categorie getCategorieById(Long id) {
        Optional<Categorie> club = categorieRepo.findById(id);//option est une class pour tester categorie vide ou non
        return club.orElse(null);//orElse qui test null ou non
    }
    @Override
    public List<Categorie> findByNomCatContains(String nom) {
        return categorieRepo.findByNomCatContaining(nom);
    }
    /*@Override
    public List<Categorie> findByNomCategorie(String nom) {
        return categorieRepo.findByNomCategorie(nom);
    }
*/
    /*@Override
    public List<Categorie> findByNomCategorieContains(String nom) {
        return categorieRepo.findByNomCategorieContains(nom);
    }*/
/*
    @Override
    public List<Categorie> findByDescription(String description) {
        return categorieRepo.findByDescription(description);
    }
*/
}
