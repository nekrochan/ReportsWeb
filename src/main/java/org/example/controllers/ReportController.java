package org.example.controllers;

import org.example.models.Report;
import org.example.service.dto.ReportDto;
import org.example.service.interfaces.ReportService;
import org.example.views.ReportViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports/add")
    public Report addReport(@RequestBody ReportDto reportDto) {
        reportService.addReport(reportDto);
        return this.reportService.findReportByTheme(reportDto.getTheme());
    }

    @PutMapping("/reports/{theme}")
    public Report updateReport(@PathVariable String theme, @RequestBody ReportDto newData){
        newData.setId(reportService.findReportByTheme(theme).getId());
        this.reportService.addReport(newData);
        return this.reportService.findReportByTheme(theme);
    }

    @DeleteMapping("/reports/{theme}")
    public void deleteReport(@PathVariable String theme){
        reportService.deleteReport(theme);
    }

    @GetMapping("/reports/{theme}")
    public Report getReportByTheme(@PathVariable String theme){
        return reportService.findReportByTheme(theme);
    }

    @GetMapping("/reports")
    public List<ReportViewModel> getAllReports() {
        return reportService.findAllReports();
    }
}
