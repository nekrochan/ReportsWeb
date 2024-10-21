package org.example.controllers;

import org.example.models.Conference;
import org.example.service.dto.ConferenceDto;
import org.example.service.interfaces.ConferenceService;
import org.example.views.ConferenceViewModel;
import org.example.views.ReportViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    // Найти конференцию по названию
    @GetMapping("/conferences/{name}")
    public Conference getConferenceByName(@PathVariable String name){
        return this.conferenceService.findConferenceByConfName(name);
    }

    // Добавить новую конференцию
    @PostMapping("/conferences/add")
    public Conference addConference(@RequestBody ConferenceDto conferenceDto){
        try {
            this.conferenceService.addConference(conferenceDto);
            return this.conferenceService.findConferenceByConfName(conferenceDto.getConfName());
        } catch (Exception e) {
        }
        return null;
    }

    // Редактировать запись о конференции
    @PutMapping("/conferences/{name}")
    public Conference updateConference(@PathVariable String name, @RequestBody ConferenceDto newData) {
        newData.setId(this.conferenceService.findConferenceByConfName(name).getId());
        this.conferenceService.addConference(newData);
        return this.conferenceService.findConferenceByConfName(name);
    }

    // Удалить запись о конференции
    @DeleteMapping("/conferences/{name}")
    public void deleteConference(@PathVariable String name) {
        conferenceService.deleteConference(name);
    }

    // Найти все доклады по названию конференции
    @GetMapping("/conferences/{name}/reports")
    public List<ReportViewModel> getReportsByConferenceName(@PathVariable String name) {
        return conferenceService.findAllReportsFromConference(name);
    }

    // Найти все конференции
    @GetMapping("/conferences/")
    public List<ConferenceViewModel> getAllConferences() {
        return conferenceService.findAllConferences();
    }
}
