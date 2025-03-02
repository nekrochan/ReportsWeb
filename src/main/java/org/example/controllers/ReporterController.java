package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.ReporterDto;
import org.example.service.impl.ReporterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/reporters")
public class ReporterController {

    @Autowired
    private final ReporterServiceImpl reporterServiceImpl;

    public ReporterController(ReporterServiceImpl reporterServiceImpl) {
        this.reporterServiceImpl = reporterServiceImpl;
    }

    @GetMapping("/add")
    public String addReporter() {
        log.info("Get Request:\treporter-add page");
        return "reporter-add";
    }

    @ModelAttribute("reporterModel")
    public ReporterDto initReporter() {
        return new ReporterDto();
    }

    @PostMapping("/add")
    public String addReporter(@Valid ReporterDto reporterModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("Post Request:\treporter-add page");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reporterModel", reporterModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reporterModel",
                    bindingResult);

            log.info("Response for Post Request reporter-add:\tunable to add reporter with name ".concat(reporterModel.getReporterName()));
            return "redirect:/reporters/add";
        }
        reporterServiceImpl.addReporter(reporterModel);

        log.info("Response for Post Request reporter-add:\treporter added successfully: ".concat(reporterModel.getReporterName()));
        return "redirect:/reporters/all";
    }

    @GetMapping("/all")
    public String showAllReporters(Model model) {
        log.info("Get Request:\treporter-all page");
        model.addAttribute("allReporters", reporterServiceImpl.findAllReporters());

        log.info("Response for Get Request reporter-all:\treturning reporter-all page");
        return "reporter-all";
    }

    @GetMapping("/reporter-by-name/{reporter-name}")
    public String reporterByName(@PathVariable("reporter-name") String reporterName, Model model) {
        log.info("Get Request:\treporter-by-name page with reporter name ".concat(reporterName));

        model.addAttribute("reporterByName", reporterServiceImpl.findReporterByReporterName(reporterName));

        log.info("Response for Get Request reporter-by-name:\treturning reporter-by-name/".concat(reporterName));
        return "reporter-by-name";
    }

    @GetMapping("/reporter-delete/{reporter-by-name}")
    public String deleteReporter(@PathVariable("reporter-by-name") String reporterName, RedirectAttributes redirectAttributes) {
        log.info("Get Request:\tdelete reporter with name ".concat(reporterName));
        try {
            reporterServiceImpl.deleteReporter(reporterName);
            log.info("Response for Get Request delete reporter:\treporter deleted: ".concat(reporterName));
            return "redirect:/reporters/all";
        } catch (Exception e) {
            log.info("Response for Get Request delete reporter:\tUnable to delete reporter: ".concat(reporterName));
            redirectAttributes.addFlashAttribute("UnableToDelete", true);
        }

        return "redirect:/reporters/reporter-by-name/{reporter-Name}";
    }
}