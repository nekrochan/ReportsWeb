package org.example.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReportDto {
    private String id;
    private String theme;
    private int volume;
    private String reporterName;
    private String confName;

    public ReportDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Min(1)
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }

    @NotNull
    @NotEmpty
    public String getReporterName() {
        return reporterName;
    }
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    @NotNull
    @NotEmpty
    public String getConfName() {
        return confName;
    }
    public void setConfName(String confName) {
        this.confName = confName;
    }
}
