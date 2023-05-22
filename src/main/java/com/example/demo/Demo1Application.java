package com.example.demo;

import com.example.demo.produits.dao.CategorieRepo;
import com.example.demo.produits.dao.UserRepository;
import com.example.demo.produits.entities.Categorie;
import com.example.demo.produits.entities.Produit;
import com.example.demo.produits.entities.User;
import com.example.demo.produits.service.ProduitServiceImpl;
import com.example.demo.produits.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Demo1Application implements CommandLineRunner {
    @Autowired
    private ProduitServiceImpl service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
private CategorieRepo catrepo;
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        Categorie cat1=new Categorie("cat1","le premier categorie",null);
        Produit prod1 = new Produit("PC Asus",1500.500,new Date(),cat1);
        Produit prod2 = new Produit("PC zell",2000.500,new Date(),cat1);
        Produit prod3 = new Produit("PC Toshiba",2500.500,new Date());
        Produit prod4 = new Produit("iphone X",1000.0,new Date());
        User us1=new User("rania","123");
        catrepo.save(cat1);
        userRepository.save(us1);
        service.saveProduit(prod1);
        service.saveProduit(prod2);
        service.saveProduit(prod3);
        service.saveProduit(prod4);

    }


}
