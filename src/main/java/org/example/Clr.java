package org.example;

import org.example.models.Host;
import org.example.service.dto.ConferenceDto;
import org.example.service.dto.ReportDto;
import org.example.service.dto.ReporterDto;
import org.example.service.impl.HostServiceImpl;
import org.example.service.interfaces.*;
import org.example.views.ConferenceViewModel;
import org.example.views.ReportViewModel;
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
    private final HostServiceImpl hostServiceImpl;

    @Autowired
    public Clr(ReportService reportService, ReporterService reporterService, ConferenceService conferenceService, FounderService founderService, HostServiceImpl hostServiceImpl) {
        this.reportService = reportService;
        this.reporterService = reporterService;
        this.conferenceService = conferenceService;
        this.founderService = founderService;
        this.hostServiceImpl = hostServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true){
            System.out.println("CHOOSE AN OPTION:" +
                    "\n1 - for Add Report" +
                    "\n2 - for Add Reporter" +
                    "\n3 - for Add Conference" +
                    "\n4 - for Add Founder" +
                    "\n5 - for Add Host" +
                    "\n6 - for Showing all reports from conference" +
                    "\n7 - for Showing all reports of reporter" +
                    "\n8 - for Showing all conferences"
            );


            String input = bufferedReader.readLine().toLowerCase();

            switch (input) {
                case "1":
                    this.addReport();
                    break;
                case "2":
                    this.addReporter();
                    break;
                case "3":
                    this.addConference();
                    break;
                case "4":
                    this.addFounder();
                    break;
                case "5":
                    this.addHost();
                    break;
                case "6":
                    this.ShowAllReportsFromConference();
                    break;
                case "7":
                    this.ShowAllReportsOfReporter();
                    break;
                case "8":
                    this.ShowAllConferences();
                    break;
                default:
                    System.out.println("Unknown input");
                    break;
            }
        }
    }

    private void ShowAllConferences() {
        List<ConferenceViewModel> conferenceViewModels = this.conferenceService.findAllConferences();

        conferenceViewModels.forEach(conferenceViewModel -> {
            System.out.printf("%s, year: %d\n",
                    conferenceViewModel.getName(), conferenceViewModel.getYear());
        });
    }

    private void ShowAllReportsOfReporter() throws IOException {
        System.out.println("Enter a reporter name:");
        String reporterName = this.bufferedReader.readLine();
        try{
            List<ReportViewModel> reportViewModels = this.reporterService
                    .findAllReports(reporterName);

            reportViewModels.forEach(reportViewModel -> {
                System.out.printf("%s - %n pages\n", reportViewModel.getTheme(), reportViewModel.getValue());
            });
        } catch (Exception e) {
            System.out.println("Unable to find reports from conference");
        }
    }

    private void ShowAllReportsFromConference() throws IOException {
        System.out.println("Enter a conference name:");
        String conferenceName = this.bufferedReader.readLine();
        List<ReportViewModel> reportViewModels = this.conferenceService
                .findAllReportsFromConference(conferenceName);

        reportViewModels.forEach(reportViewModel -> {
            System.out.printf("%s - %n pages\n", reportViewModel.getTheme(), reportViewModel.getValue());
        });
    }

    private void addHost() throws IOException {
        System.out.println("Enter name of host organization:");
        String hostName = this.bufferedReader.readLine();

        try {
            this.hostServiceImpl.addHost(hostName);
            System.out.println("Host organization added successfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to add the host organization!");
        }
    }

    private void addFounder() throws IOException {
        System.out.println("Enter name of founder:");
        String founderName = this.bufferedReader.readLine();

        try {
            this.founderService.addFounder(founderName);
            System.out.println("Founder added successfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to add the founder!");
        }

    }

    private void addConference() throws IOException {
        System.out.println("Enter conference details: " +
                "conference name, year, host organization name, founder name. " +
                "Enter it separated by ;");
        String[] confParams = this.bufferedReader.readLine()
                .split(";");

        ConferenceDto conferenceDto = new ConferenceDto();

        conferenceDto.setConfName(confParams[0]);
        conferenceDto.setYear(Integer.valueOf(confParams[1]));
        conferenceDto.setHostName(confParams[2]);
        conferenceDto.setHostName(confParams[3]);

        try {
            this.conferenceService.addConference(conferenceDto);
            System.out.println("Conference added successfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to add the conference!");
        }
    }

    private void addReporter() throws IOException {
        System.out.println("Enter reporter details: " +
                "reporter name, competence, submitting organization. " +
                "Enter it separated by ;");
        String[] reporterParams = this.bufferedReader.readLine()
                .split(";");

        ReporterDto reporterDto = new ReporterDto();

        reporterDto.setReporterName(reporterParams[0]);
        reporterDto.setCompetence(reporterParams[1]);
        reporterDto.setOrganization(reporterParams[2]);

        try {
            this.reporterService.addReporter(reporterDto);
            System.out.println("Reporter added successfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to add the reporter!");
        }
    }

    private void addReport() throws IOException {
        System.out.println("Enter report details: " +
                "theme, reporter name, conference name, volume. " +
                "Enter it separated by ;");
        String[] reportParams = this.bufferedReader.readLine()
                .split(";");

        ReportDto reportDto = new ReportDto();

        reportDto.setTheme(reportParams[0]);
        reportDto.setReporterName(reportParams[1]);
        reportDto.setConfName(reportParams[2]);
        reportDto.setVolume(Integer.valueOf(reportParams[3]));

        try {
            this.reportService.addReport(reportDto);
            System.out.println("Report added successfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to add the report!");
        }
    }

}
