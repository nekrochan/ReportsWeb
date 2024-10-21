package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Report;
import org.example.repositories.ReportRepository;
import org.example.service.dto.ReportDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.ReportService;
import org.example.service.interfaces.ReporterService;
import org.example.utils.ValidationUtil;
import org.example.views.ReportViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final ReporterService reporterService;
    private final ConferenceService conferenceService;

    @Autowired
    public ReportServiceImpl(
            ReportRepository reportRepository,
            ModelMapper modelMapper,
            ValidationUtil validationUtil,
            ReporterService reporterService,
            ConferenceService conferenceService
    ) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.reporterService = reporterService;
        this.conferenceService = conferenceService;
    }


    @Override
    public void addReport(ReportDto reportDto) {
        if (!this.validationUtil.isValid(reportDto)) {

            this.validationUtil
                    .violations(reportDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            throw new IllegalArgumentException("Illegal arguments!");
        }

        Report report = this.modelMapper.map(reportDto, Report.class);
        report.setReporter(reporterService.findReporterByReporterName(reportDto.getReporterName()));
        report.setConference(conferenceService.findConferenceByConfName(reportDto.getConfName()));

        this.reportRepository.saveAndFlush(report);
    }

    @Override
    public Report findReportByTheme(String theme) {
        return this.reportRepository.findByTheme(theme);
    }

    @Override
    public List<ReportViewModel> findAllReports() {
        return this.reportRepository
                .findAll()
                .stream()
                .map(report -> modelMapper.map(report, ReportViewModel.class))
                .collect(Collectors.toList());
    }
}
