package com.demo.demo.vehicule.controller.marque;

import com.demo.demo.api.APIResponse;
import com.demo.demo.vehicule.model.marque.Marque;
import com.demo.demo.vehicule.service.marque.MarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marques")
@CrossOrigin("*")
public class MarqueController {

    private final MarqueService marqueService;

    @Autowired
    public MarqueController(MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse> createMarque(@RequestBody Marque marque) {
        try {
            Marque createdMarque = marqueService.createMarque(marque);
            return ResponseEntity.ok(new APIResponse("Marque créée avec succès", createdMarque));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la création de la marque: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<APIResponse> getAllMarques() {
        try {
            List<Marque> marques = marqueService.getAllMarques();
            return ResponseEntity.ok(new APIResponse("", marques));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de toutes les marques: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getMarqueById(@PathVariable int id) {
        try {
            Optional<Marque> marque = marqueService.getMarqueById(id);
            if (marque.isPresent()) {
                return ResponseEntity.ok(new APIResponse("", marque.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de la marque: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateMarque(@PathVariable int id, @RequestBody Marque marque) {
        try {
            Marque updatedMarque = marqueService.updateMarque(id, marque);
            if (updatedMarque != null) {
                return ResponseEntity.ok(new APIResponse("", updatedMarque));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la mise à jour de la marque: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteMarque(@PathVariable int id) {
        try {
            marqueService.deleteMarque(id);
            return ResponseEntity.ok(new APIResponse("", true));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la suppression de la marque: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
