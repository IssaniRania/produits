package com.example.demo.produits.controller;

import com.example.demo.produits.dao.CategorieRepo;
import com.example.demo.produits.dao.ProduitRepository;
import com.example.demo.produits.entities.Categorie;
import com.example.demo.produits.entities.Produit;
import com.example.demo.produits.service.CategorieService;
import com.example.demo.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CatController {
    @Autowired
    ProduitService produitService;
@Autowired
    CategorieService categorieService;
@Autowired
    CategorieRepo categorieRepo;
   /* @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap) {
        modelMap.addAttribute("produit", new Produit());
        return "createProduit";
    }

    @RequestMapping("/saveProduit")
    public String saveProduit(@Valid Produit produit,
                              BindingResult bindingResult,
                              ModelMap modelMap) {
            if (bindingResult.hasErrors()) {
                return "createProduit";
            }
        Produit saveProduit = produitService.saveProduit(produit);
        String msg = "produit enregistré avec Id " + saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }
*/
    //liste
    @RequestMapping("/ListeProduits")
    public String listeProduits(
            ModelMap modelMap,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeProduits";
    }

    //supprimer
    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id, ModelMap
            modelMap,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "2") int size) {
        produitService.deleteProduitById(id);
        Page<Produit> prods = produitService.getAllProduitsParPage(page,
                size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeProduits";
    }

    //modifier
    @RequestMapping("/modifierProduit")
    public String editerProduit(@RequestParam("id") Long id, ModelMap modelMap) {
        Produit p = produitService.getProduit(id);
        if (p == null) {
            modelMap.addAttribute("errorMessage", "Produit introuvable");
            return "error";
        }
        modelMap.addAttribute("produit", p);
        return "editeProduit";
    }

    @RequestMapping("/updateProduit")
    public String updateProduit(@Valid Produit produit,
                                BindingResult bindingResult,
                                @RequestParam("id") Long id,
                                ModelMap modelMap) throws ParseException {


        if (bindingResult.hasErrors()) {
            return "editeProduit";
        }
        Produit existingpd = produitService.getProduit(id);
        if (existingpd == null) {
            modelMap.addAttribute("errorMessage", "Produit introuvable");
            return "error";
        }
        existingpd.setNomProduit(produit.getNomProduit());
        existingpd.setPrixProduit(produit.getPrixProduit());
        modelMap.addAttribute("categories", categorieService.getAllCategories());
        try {
            produitService.updateProduit(existingpd);
        } catch (Exception e) {
            modelMap.addAttribute("errorMessage", "Une erreur est survenue lors de la mise à jour du produit");
            return "error";
        }
        produitService.updateProduit(existingpd);
        List<Produit> pds = produitService.getAllProduits();
        modelMap.addAttribute("produits", pds);
        return "listeProduits";
    }

// Categorie
@RequestMapping("/categorie")
public String categorie(ModelMap modelMap) {
    modelMap.addAttribute("categorie", new Categorie());
    return "createCategorie";
}
    @RequestMapping("/saveCategorie")
    public String saveCategorie(@Valid Categorie categorie,
                              BindingResult bindingResult,
                              ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return "createCategorie";
        }
        Categorie saveCategorie = categorieService.saveCategorie(categorie);
        String msg = "categorie  enregistré avec Id " + saveCategorie.getIdCat();
        modelMap.addAttribute("msg", msg);
        return "createCategorie";
    }
    @RequestMapping("/ListeCategorie")
    public String listeCategorie(
            ModelMap modelMap,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Categorie> cats = categorieService.getAllCategoriesParPage(page, size);
        modelMap.addAttribute("categories", cats);
        modelMap.addAttribute("pages", new int[cats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "ListeCategorie";
    }
    @RequestMapping("/supprimerCategorie")
    public String supprimerCategorie(@RequestParam("id") Long id, ModelMap modelMap,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        try {
            categorieService.deleteCategorieById(id);
        } catch (DataIntegrityViolationException e) {
            modelMap.addAttribute("errorMessage", "Impossible de supprimer la catégorie. Elle a des produits associés.");
            return "ListeCategorie";
        }

        Page<Categorie> cats = categorieService.getAllCategoriesParPage(page, size);
        modelMap.addAttribute("categories", cats);
        modelMap.addAttribute("pages", new int[cats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "ListeCategorie";
    }
    @RequestMapping("/modifierCategorie")
    public String modifierCategorie(@RequestParam("id") Long id, ModelMap modelMap) {
        Categorie cat = categorieService.getCategorie(id);
        modelMap.addAttribute("categorie", cat);
        return "editeCategorie";
    }

    @RequestMapping("/updateCategorie")
    public String updateCategorie(@Valid Categorie categorie,
                                  BindingResult bindingResult,
                                  @RequestParam("id") Long id,
                                   ModelMap modelMap) throws ParseException {
        if (bindingResult.hasErrors()) {
            return "createCategorie";
        }
        Categorie existingCat = categorieService.getCategorie(id);
        existingCat.setNomCat(categorie.getNomCat());
        existingCat.setDescriptionCat(categorie.getDescriptionCat());

        categorieService.updateCategorie(existingCat);
        List<Categorie> cats = categorieService.getAllCategories();
        modelMap.addAttribute("categories", cats);

        return "listeCategorie";
    }

   /*@RequestMapping("/dropdown")
   public String dropdownPage(ModelMap modelMap) {
       return "template";
   }
*/
   @RequestMapping("/search")
   public String search(@RequestParam("name") String name, ModelMap modelMap) {
       // Rechercher les produits qui correspondent au critère de recherche
       List<Produit> produits = produitService.findByNomProduitContains(name);

       // Ajouter les produits à l'objet Model pour les afficher dans la vue
       modelMap.addAttribute("produits", produits);
       modelMap.addAttribute("searchTerm", name);
       // Renvoyer la vue qui affiche les résultats de la recherche
       return "searchResults";
   }

   @RequestMapping("/showCreate")
    public String showCreate(@RequestParam(name = "id", required = false) Long idCateg, ModelMap modelMap) {
        Categorie cat = null;
        if (idCateg != null && !idCateg.equals("null")) {
            try {
                Long Id = Long.parseLong(String.valueOf(idCateg));
                cat = categorieService.getCategorie(Id);
            } catch (NumberFormatException e) {
                modelMap.addAttribute("errorMessage", "Erreur : le club est null.");
            }
        }

        modelMap.addAttribute("categorie", cat);
        modelMap.addAttribute("produit", new Produit());
        List<Categorie> categories = categorieRepo.findAll();
        modelMap.addAttribute("categories", categories);
        return "createProduit";
    }
    @RequestMapping("/saveProduit")
    public String saveProduit(@Valid Produit produit,
                              BindingResult bindingResult,
                              @RequestParam(value = "categorieId", required = false) Long IdCateg,
                              ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return "createProduit";
        }
        Categorie cat = null;
        if (IdCateg != null) {
            cat = categorieRepo.findById(IdCateg).orElse(null);
        }

        produit.setCategorie(cat); // Ajouter la catégorie au produit

        Produit saveProduit = produitService.saveProduit(produit);
        String msg = "produit enregistré avec Id " + saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        if (IdCateg!= null) {
            return "redirect:/showCreate?id=" + IdCateg;
        } else {
            return "redirect:/showCreate";
        }

    }
    @RequestMapping("/searchCategorie")
    public String searchCategorie(@RequestParam("name") String name, ModelMap modelMap) {
        // Rechercher les catégories qui correspondent au critère de recherche
        List<Categorie> categories = categorieService.findByNomCatContains(name);

        // Ajouter les catégories à l'objet Model pour les afficher dans la vue
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("searchTerm", name);

        // Renvoyer la vue qui affiche les résultats de la recherche
        return "searchCategorie";
    }
}
