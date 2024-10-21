package org.example.service.interfaces;

import org.example.models.Report;
import org.example.service.dto.ReportDto;
import org.example.views.ReportViewModel;

import java.util.List;

public interface ReportService {
    void addReport(ReportDto reportDto);
    Report findReportByTheme(String theme);
    List<ReportViewModel> findAllReports();
    Report updateReport(ReportDto reportDto);
    void deleteReport(String theme);
}
