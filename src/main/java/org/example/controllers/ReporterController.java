package org.example.controllers;

import org.example.models.Reporter;
import org.example.service.dto.ReportDto;
import org.example.service.dto.ReporterDto;
import org.example.service.interfaces.ReporterService;
import org.example.views.ReporterViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reporters")
public class ReporterController {
    private final ReporterService reporterService;

    @Autowired public ReporterController(ReporterService reporterService) {
        this.reporterService = reporterService;
    }

    @PostMapping("/reporters/add")
    public Reporter addReporter(ReporterDto reporter) {
        reporterService.addReporter(reporter);
        return reporterService.findReporterByReporterName(reporter.getReporterName());
    }

    @PutMapping("/reporters/{name}")
    public Reporter updateReporter(@PathVariable String name, @RequestBody ReporterDto newData){
        newData.setId(reporterService.findReporterByReporterName(name).getId());
        reporterService.addReporter(newData);
        return reporterService.findReporterByReporterName(name);
    }

    @DeleteMapping("/reporters/{name}")
    public void deleteReporter(@PathVariable String name){
        reporterService.deleteReporter(name);
    }

    @GetMapping("/reporters")
    public List<ReporterViewModel> getAllReporters(){
        return reporterService.findAllReporters();
    }

    @GetMapping("/reporters/{name}")
    public Reporter getReporter(@PathVariable String name){
        return reporterService.findReporterByReporterName(name);
    }
}
