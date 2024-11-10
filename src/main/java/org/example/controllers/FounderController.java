package org.example.controllers;

import org.example.service.dto.FounderDto;
import org.example.service.interfaces.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FounderController {

    @Autowired
    private FounderService founderService;

    @GetMapping("/founders")
    public List<FounderDto> getFounders(){
        return founderService.findAllFounders();
    }
}
