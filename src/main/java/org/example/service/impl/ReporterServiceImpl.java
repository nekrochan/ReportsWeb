package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Report;
import org.example.models.Reporter;
import org.example.repositories.ReporterRepository;
import org.example.service.dto.ReporterDto;
import org.example.service.interfaces.ReporterService;
import org.example.utils.ValidationUtil;
import org.example.views.ReportViewModel;
import org.example.views.ReporterViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporterServiceImpl implements ReporterService {

    private final ReporterRepository reporterRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ReporterServiceImpl(
            ReporterRepository reporterRepository,
            ModelMapper modelMapper,
            ValidationUtil validationUtil) {
        this.reporterRepository = reporterRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addReporter(ReporterDto reporterDto) {

        if (!this.validationUtil.isValid(reporterDto)) {
            this.validationUtil
                    .violations(reporterDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

        }
        Reporter reporter = this.modelMapper.map(reporterDto, Reporter.class);
        this.reporterRepository.saveAndFlush(reporter);
    }

    @Override
    public Reporter findReporterByReporterName(String name) {
        return this.reporterRepository.findReporterByReporterName(name);
    }

    @Override
    public List<ReportViewModel> findAllReports(String name) {
        return this.reporterRepository
                .findReporterByReporterName(name)
                .getReports()
                .stream()
                .map(report -> this.modelMapper.map(report, ReportViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporterViewModel> findAllReporters() {
        return this.reporterRepository
                .findAll()
                .stream()
                .map(reporter -> this.modelMapper.map(reporter, ReporterViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Reporter updateReporter(ReporterDto reporterDto) {
        if (!this.validationUtil.isValid(reporterDto)) {
            this.validationUtil
                    .violations(reporterDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

        }
        Reporter reporter = this.modelMapper.map(reporterDto, Reporter.class);
        return this.reporterRepository.saveAndFlush(reporter);
    }

    @Override
    public void deleteReporter(String reporterName) {
        /*
        this.reporterRepository.delete(
                this.reporterRepository.findReporterByReporterName(reporterName)
        );

         */
        Reporter reporter = reporterRepository.findReporterByReporterName(reporterName);
        if (reporter != null) {
            reporterRepository.delete(reporter);
        } else {
            // Handle case where report is not found
            throw new IllegalArgumentException("Report with theme '" + reporterName + "' not found.");
        }
    }


}
