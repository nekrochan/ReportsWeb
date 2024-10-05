package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Report;
import org.example.models.Reporter;
import org.example.repositories.ReporterRepository;
import org.example.service.dto.ReporterDto;
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
public class ReporterServiceImpl implements ReporterService {

    private final ReporterRepository reporterRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ReporterServiceImpl(ReporterRepository reporterRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
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
    public Reporter findReporterByName(String name) {
        return this.reporterRepository.findReporterByName(name);
    }

    @Override
    public List<ReportViewModel> findAllReports(String name) {
        return this.reporterRepository
                .findReporterByName(name)
                .getReports()
                .stream()
                .map(product -> this.modelMapper.map(product, ReportViewModel.class))
                .collect(Collectors.toList());
    }


}
