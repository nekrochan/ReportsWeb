package org.example.controllers;

import org.example.service.dto.FounderDto;
import org.example.service.interfaces.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/founders")
public class FounderController {

    @Autowired
    private FounderService founderService;

    public FounderController(FounderService founderService) {
        this.founderService = founderService;
    }

    @GetMapping("/add")
    public String addFounder() {
        return "founder-add";
    }

    @ModelAttribute("founderModel")
    public FounderDto initFounder() {
        return new FounderDto();
    }

    @PostMapping("/add")
    public String addFounder(@Valid FounderDto founderModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("founderModel", founderModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.founderModel",
                    bindingResult);
            return "redirect:/founders/add";
        }
        founderService.addFounder(founderModel.getFounderName());

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        model.addAttribute("founderInfos", founderService.findAllFounders());

        return "founder-all";
    }

    @GetMapping("/founder-by-name/{founder-name}")
    public String founderDetails(@PathVariable("founder-name") String founderName, Model model) {
        model.addAttribute("founderByName", founderService.findFounderByFounderName(founderName));

        return "founder-by-name";
    }

    @GetMapping("/founder-delete/{founder-name}")
    public String deleteFounder(@PathVariable("founder-name") String founderName) {
        founderService.deleteFounder(founderName);

        return "redirect:/founders/all";
    }
}
