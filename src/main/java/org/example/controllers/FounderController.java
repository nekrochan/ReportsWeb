package org.example.controllers;

import org.example.models.Founder;
import org.example.service.dto.FounderDto;
import org.example.service.interfaces.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/founders")
public class FounderController {

    private final FounderService founderService;

    @Autowired
    public FounderController(FounderService founderService) {
        this.founderService = founderService;
    }

    @PostMapping("founders/add")
    public Founder addFounder(@RequestBody String name) {
        founderService.addFounder(name);
        return founderService.findFounderByFounderName(name);
    }

    @PutMapping("/founders/{name}")
    public Founder updateFounder(@PathVariable String name, @PathVariable String newName){
        this.founderService
                .findFounderByFounderName(name)
                .setFounderName(newName);
        return this.founderService.findFounderByFounderName(newName);
    }

    @DeleteMapping("founders/{id}")
    public void deleteFounder(@PathVariable String name){
        this.founderService.deleteFounder(name);
    }

    @GetMapping("/founders")
    public List<FounderDto> getAllFounders() {
        return founderService.findAllFounders();
    }
}
