package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private final Taxable taxService;
    private final Map<Integer, Developer> developers = new HashMap<>();

    public DeveloperController(Taxable taxService) {
        this.taxService = taxService;
    }

    @PostConstruct
    public void init() {
        developers.put(1, new Developer(1, "Initial Developer", 5000.0, com.workintech.s17d2.model.Experience.JUNIOR));
    }

    @GetMapping
    public Collection<Developer> getDevelopers() {
        return developers.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable int id) {
        Developer developer = developers.get(id);
        if (developer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(developer);
    }

    @PostMapping
    public ResponseEntity<Void> addDeveloper(@RequestBody Developer developer) {
        developers.put(developer.getId(), developer);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDeveloper(@PathVariable int id, @RequestBody Developer updatedDeveloper) {
        if (!developers.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        developers.put(id, updatedDeveloper);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable int id) {
        if (!developers.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        developers.remove(id);
        return ResponseEntity.ok().build();
    }
}
