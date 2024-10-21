package org.example.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "conferences")
public class Conference extends BaseEntity{
    private String confName;
    private int year;
    private Set<Report> reports;
    private Host host;
    private Founder founder;

    public Conference() {
    }

    @Column(name = "confName")
    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @OneToMany(mappedBy = "conference", fetch = FetchType.EAGER)
    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    @ManyToOne
    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @ManyToOne
    public Founder getFounder() {
        return founder;
    }

    public void setFounder(Founder founder) {
        this.founder = founder;
    }
}
