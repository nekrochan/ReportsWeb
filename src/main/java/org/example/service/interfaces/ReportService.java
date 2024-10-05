package org.example.service.interfaces;

import org.example.models.Report;
import org.example.service.dto.ReportDto;

public interface ReportService {
    void addReport(ReportDto reportDto);

    Report findReportByTheme(String name);

}
