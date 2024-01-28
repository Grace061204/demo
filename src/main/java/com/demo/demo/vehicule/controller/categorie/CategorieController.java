package com.demo.demo.vehicule.controller.categorie;

import com.demo.demo.api.APIResponse;
import com.demo.demo.vehicule.model.categorie.Categorie;
import com.demo.demo.vehicule.service.categorie.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategorieController {

    public CategorieService categorieService;

    public CategorieController(CategorieService categorieService){
        this.categorieService = categorieService;
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse> createCategorie(@RequestBody Categorie categorie) {
        try {
            Categorie createdCategorie = categorieService.createCategorie(categorie);
            return ResponseEntity.ok(new APIResponse("", createdCategorie));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la création de la catégorie: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Categorie> categories = categorieService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("", categories));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de toutes les catégories: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCategorieById(@PathVariable int id) {
        try {
            Optional<Categorie> categorie = categorieService.getCategorieById(id);
            if (categorie.isPresent()) {
                return ResponseEntity.ok(new APIResponse("", categorie.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de la catégorie: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateCategorie(@PathVariable int id, @RequestBody Categorie categorie) {
        try {
            Categorie updatedCategorie = categorieService.updateCategorie(id, categorie);
            if (updatedCategorie != null) {
                return ResponseEntity.ok(new APIResponse("", updatedCategorie));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la mise à jour de la catégorie: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCategorie(@PathVariable int id) {
        try {
            categorieService.deleteCategorie(id);
            return ResponseEntity.ok(new APIResponse("", true));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la suppression de la catégorie: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
