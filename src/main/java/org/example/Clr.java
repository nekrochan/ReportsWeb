package org.example;

import org.example.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class Clr implements CommandLineRunner {

    private final BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));

    private final ReportService reportService;
    private final ReporterService reporterService;
    private final ConferenceService conferenceService;
    private final FounderService founderService;
    private final HostService hostService;

    @Autowired
    public Clr(ReportService reportService, ReporterService reporterService, ConferenceService conferenceService, FounderService founderService, HostService hostService) {
        this.reportService = reportService;
        this.reporterService = reporterService;
        this.conferenceService = conferenceService;
        this.founderService = founderService;
        this.hostService = hostService;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
