package org.example.service.interfaces;

import org.example.models.Reporter;
import org.example.service.dto.ReporterDto;
import org.example.views.ReportViewModel;

import java.util.List;

public interface ReporterService {
    void addReporter(ReporterDto reporterDto);
    Reporter findReporterByName(String name);

    List<ReportViewModel> findAllReports(String name);
}
