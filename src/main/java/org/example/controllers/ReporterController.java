package org.example.controllers;

import org.example.service.dto.ReporterDto;
import org.example.service.interfaces.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reporters")
public class ReporterController {
    
    @Autowired
    private final ReporterService reporterService;

    public ReporterController(ReporterService reporterService) {
        this.reporterService = reporterService;
    }

    @GetMapping("/add")
    public String addReporter() {
        return "reporter-add";
    }

    @ModelAttribute("reporterModel")
    public ReporterDto initReporter() {
        return new ReporterDto();
    }

    @PostMapping("/add")
    public String addReporter(@Valid ReporterDto reporterModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reporterModel", reporterModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reporterModel",
                    bindingResult);
            return "redirect:/reporters/add";
        }
        reporterService.addReporter(reporterModel);

        return "redirect:/reporters/all";
    }

    @GetMapping("/all")
    public String showAllReporters(Model model) {
        model.addAttribute("allReporters", reporterService.findAllReporters());

        return "reporter-all";
    }

    @GetMapping("/reporter-by-name/{reporter-name}")
    public String reporterByName(@PathVariable("reporter-name") String reporterName, Model model) {
        model.addAttribute("reporterByName", reporterService.findReporterByReporterName(reporterName));

        return "reporter-by-name";
    }

    @GetMapping("/reporter-delete/{reporter-by-name}")
    public String deleteReporter(@PathVariable("reporter-by-name") String reporterName) {
        reporterService.deleteReporter(reporterName);

        return "redirect:/reporters/all";
    }
}
