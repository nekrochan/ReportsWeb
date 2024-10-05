package org.example.service.interfaces;

import org.example.models.Reporter;
import org.example.service.dto.ReporterDto;

public interface ReporterService {
    void addReporter(ReporterDto reporterDto);
    Reporter findReporterByName(String name);
}
