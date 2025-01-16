package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        log.info("Get Request:\treport-add page");

        model.addAttribute("reportModel", new ReportDto());

        List<ConferenceViewModel> conferences = conferenceService.findAllConferences();
        List<ReporterViewModel> reporters = reporterService.findAllReporters();

        model.addAttribute("conferences", conferences);
        model.addAttribute("reporters", reporters);

        if (conferences.isEmpty() || reporters.isEmpty()) {
            model.addAttribute("noOptions", true);
        }

        log.info("Response for Get Request report-add:\treturning report-add page");
        return "report-add";
    }

    @ModelAttribute("reportModel")
    public ReportDto initReport() {
        return new ReportDto();
    }

    @PostMapping("/add")
    public String addReport(@Valid ReportDto reportModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("Post Request:\treport-add page");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportModel", reportModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportModel",
                    bindingResult);

            log.info("Response for Post Request report-add:\tunable to add report with theme ".concat(reportModel.getTheme()));
            return "redirect:/reports/add";
        }
        reportService.addReport(reportModel);

        log.info("Response for Post Request report-add:\treport added successfully: ".concat(reportModel.getTheme()));
        return "redirect:/reports/all";
    }

    @GetMapping("/all")
    public String showAllReports(Model model) {
        log.info("Get Request:\treport-all page");
        model.addAttribute("allReports", reportService.findAllReports());

        log.info("Response for Get Request report-all:\treturning report-all page");
        return "report-all";
    }

    @GetMapping("/report-by-theme/{report-theme}")
    public String reportByTheme(@PathVariable("report-theme") String theme, Model model) {
        log.info("Get Request:\treport-by-theme page with report theme ".concat(theme));

        model.addAttribute("reportByTheme", reportService.findReportByTheme(theme));

        log.info("Response for Get Request report-by-theme:\treturning report-by-theme/".concat(theme));
        return "report-by-theme";
    }

    @GetMapping("/report-delete/{report-by-theme}")
    public String deleteReport(@PathVariable("report-by-theme") String theme) {
        log.info("Get Request:\tdelete report with theme ".concat(theme));
        reportService.deleteReport(theme);

        log.info("Response for Get Request delete report:\treport deleted: ".concat(theme));
        return "redirect:/reports/all";
    }

    @GetMapping("/report-edit/{report-theme}")
    public String editReport(@PathVariable("report-theme") String theme, Model model) {
        log.info("Get Request:\treport-edit page with report theme ".concat(theme));

        Report report = reportService.findReportByTheme(theme);
        ModelMapper modelMapper = new ModelMapper();
        if (report != null) {
            ReportDto reportDto = modelMapper.map(report, ReportDto.class);
            reportDto.setConfName(report.getConference().getConfName());
            reportDto.setReporterName(report.getReporter().getReporterName());
            model.addAttribute("reportModel", reportDto);
            model.addAttribute("conferences", conferenceService.findAllConferences());
            model.addAttribute("reporters", reporterService.findAllReporters());

            log.info("Response for Get Request report-edit:\treturning report-edit page for theme ".concat(theme));
            return "report-edit";
        } else {
            log.info("Response for Get Request report-edit:\tunable to find report with theme ".concat(theme));
            return "redirect:/reports/all";
        }
    }

    @PostMapping("/report-edit/{report-theme}")
    public String updateReport(@PathVariable("report-theme") String theme,
                               @Valid ReportDto reportModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        log.info("Post Request:\treport-edit page with report theme ".concat(theme));

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reportModel", reportModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportModel", bindingResult);

            log.info("Response for Post Request report-edit:\tunable to save updated report with theme ".concat(theme));
            return "redirect:/reports/report-edit/" + theme;
        }

        reportService.updateReport(reportModel);

        log.info("Response for Post Request report-edit:\treport updated successfully: ".concat(theme));
        return "redirect:/reports/report-by-theme/" + reportModel.getTheme();
    }
}