package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.FounderDto;
import org.example.service.interfaces.FounderService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
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
        log.info("founder-add page requested");
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

            log.info("!! Unable to add founder: ".concat(founderModel.getFounderName()));
            return "redirect:/founders/add";
        }
        founderService.addFounder(founderModel.getFounderName());

        log.info("Founder added successfully: ".concat(founderModel.getFounderName()));
        return "redirect:/founders/all";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        model.addAttribute("allFounders", founderService.findAllFounders());

        log.info("founder-all page requested");
        return "founder-all";
    }

    @GetMapping("/founder-by-name/{founder-name}")
    public String founderDetails(@PathVariable("founder-name") String founderName, Model model) {
        model.addAttribute("founderByName", founderService.findFounderByFounderName(founderName));

        log.info("founder-by-name page requested with founder name: ".concat(founderName));
        return "founder-by-name";
    }

    @GetMapping("/founder-delete/{founder-name}")
    public String deleteFounder(@PathVariable("founder-name") String founderName) {
        founderService.deleteFounder(founderName);

        log.info("Deleted founder: ".concat(founderName));
        return "redirect:/founders/all";
    }
}
