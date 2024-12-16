package org.example.controllers;

import org.example.models.Conference;
import org.example.models.Report;
import org.example.models.Reporter;
import org.example.service.dto.ReportDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.ReportService;
import org.example.service.interfaces.ReporterService;
import org.example.views.ConferenceViewModel;
import org.example.views.ReporterViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private final ReportService reportService;
    @Autowired
    private final ConferenceService conferenceService;

    @Autowired
    private final ReporterService reporterService;

    public ReportController(ReportService reportService,
                            ConferenceService conferenceService,
                            ReporterService reporterService) {
        this.reportService = reportService;
        this.conferenceService = conferenceService;
        this.reporterService = reporterService;
    }

    @GetMapping("/add")
    public String addReport(Model model) {
        model.addAttribute("reportModel", new ReportDto());

        List<ConferenceViewModel> conferences = conferenceService.findAllConferences();
        List<ReporterViewModel> reporters = reporterService.findAllReporters();

        model.addAttribute("conferences", conferences);
        model.addAttribute("reporters", reporters);

        if (conferences.isEmpty() || reporters.isEmpty()) {
            model.addAttribute("noOptions", true);
        }

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

        return "redirect:/reports/all";
    }

    @GetMapping("/all")
    public String showAllReports(Model model) {
        model.addAttribute("allReports", reportService.findAllReports());

        return "report-all";
    }

    @GetMapping("/report-by-theme/{report-theme}")
    public String reportByTheme(@PathVariable("report-theme") String theme, Model model) {
        model.addAttribute("reportByTheme", reportService.findReportByTheme(theme));

        return "report-by-theme";
    }

    @GetMapping("/report-delete/{report-by-theme}")
    public String deleteReport(@PathVariable("report-by-theme") String theme) {
        reportService.deleteReport(theme);

        return "redirect:/reports/all";
    }

    @GetMapping("/report-edit/{report-theme}")
    public String editReport(@PathVariable("report-theme") String theme, Model model) {
        Report report = reportService.findReportByTheme(theme);
        ModelMapper modelMapper = new ModelMapper();
        if (report != null) {
            ReportDto reportDto = modelMapper.map(report, ReportDto.class);
            reportDto.setConfName(report.getConference().getConfName());
            reportDto.setReporterName(report.getReporter().getReporterName());
            model.addAttribute("reportModel", reportDto);
            model.addAttribute("conferences", conferenceService.findAllConferences());
            model.addAttribute("reporters", reporterService.findAllReporters());
            return "report-edit";
        } else {
            // Handle case where report is not found
            return "redirect:/reports/all";
        }
    }

    @PostMapping("/report-edit/{report-theme}")
    public String updateReport(@PathVariable("report-theme") String theme,
                               @Valid ReportDto reportModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportModel", reportModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportModel", bindingResult);
            return "redirect:/reports/report-edit/" + theme;
        }

        reportService.updateReport(reportModel);

        return "redirect:/reports/report-by-theme/" + reportModel.getTheme();
    }
}
