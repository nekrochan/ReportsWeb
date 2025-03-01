package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.FounderDto;
import org.example.service.impl.FounderServiceImpl;
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
    private FounderServiceImpl founderServiceImpl;

    public FounderController(FounderServiceImpl founderServiceImpl) {
        this.founderServiceImpl = founderServiceImpl;
    }

    @GetMapping("/add")
    public String addFounder() {
        log.info("Get Request:\tfounder-add page");
        return "founder-add";
    }

    @ModelAttribute("founderModel")
    public FounderDto initFounder() {
        return new FounderDto();
    }

    @PostMapping("/add")
    public String addFounder(@Valid FounderDto founderModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("Post Request:\tfounder-add page");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("founderModel", founderModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.founderModel",
                    bindingResult);

            log.info("Response for Post Request founder-add:\tunable to add founder ".concat(founderModel.getFounderName()));
            return "redirect:/founders/add";
        }
        founderServiceImpl.addFounder(founderModel.getFounderName());

        log.info("Response for Post Request founder-add:\tfounder added successfully: ".concat(founderModel.getFounderName()));
        return "redirect:/founders/all";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        log.info("Get Request:\tfounder-all page");
        model.addAttribute("allFounders", founderServiceImpl.findAllFounders());

        log.info("Response for Get Request founder-all:\treturning founder-all page");
        return "founder-all";
    }

    @GetMapping("/founder-by-name/{founder-name}")
    public String founderDetails(@PathVariable("founder-name") String founderName, Model model) {
        log.info("Get Request:\tfounder-by-name page with founder name ".concat(founderName));

        model.addAttribute("founderByName", founderServiceImpl.findFounderByFounderName(founderName));

        log.info("Response for Get Request founder-by-name:\treturning founder-by-name/".concat(founderName));
        return "founder-by-name";
    }

    @GetMapping("/founder-delete/{founder-name}")
    public String deleteFounder(@PathVariable("founder-name") String founderName) {
        log.info("Get Request:\tdelete founder with name ".concat(founderName));
        founderServiceImpl.deleteFounder(founderName);

        log.info("Response for Get Request delete founder:\tfounder deleted: ".concat(founderName));
        return "redirect:/founders/all";
    }
}