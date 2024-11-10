package org.example.controllers;

import org.example.service.dto.ReportDto;
import org.example.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/add")
    public String addReport() {
        return "report-add";
    }

    @ModelAttribute("reportModel")
    public ReportDto initReport() {
        return new ReportDto();
    }

    @PostMapping("/add")
    public String addReport(@Valid ReportDto reportModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportModel", reportModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportModel",
                    bindingResult);
            return "redirect:/reports/add";
        }
        reportService.addReport(reportModel);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllReports(Model model) {
        model.addAttribute("reportInfos", reportService.findAllReports());

        return "report-all";
    }

    @GetMapping("/report-details/{report-theme}")
    public String reportByTheme(@PathVariable("report-theme") String theme, Model model) {
        model.addAttribute("reportByTheme", reportService.findReportByTheme(theme));

        return "report-by-theme";
    }

    @GetMapping("/report-delete/{report-theme}")
    public String deleteReport(@PathVariable("report-theme") String theme) {
        reportService.deleteReport(theme);

        return "redirect:/reports/all";
    }
}
