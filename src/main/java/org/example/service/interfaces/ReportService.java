package org.example.service.interfaces;

import org.example.models.Report;
import org.example.service.dto.ReportDto;
import org.example.views.ReportViewModel;

import java.util.List;

public interface ReportService {
    void addReport(ReportDto reportDto);

    Report findReportByTheme(String name);

    List<ReportViewModel> findAllReports();
}
