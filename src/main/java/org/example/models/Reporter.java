package org.example.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "reporters")
public class Reporter extends BaseEntity{
    private String reporterName;
    private String competence;
    private String organization;
    private Set<Report> reports;
    private String id;

    public Reporter() {
    }

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     */

    @Column(name = "reporterName")
    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    @Column(name = "competence")
    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    @Column(name = "organization")
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @OneToMany(mappedBy = "reporter")
    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
}
