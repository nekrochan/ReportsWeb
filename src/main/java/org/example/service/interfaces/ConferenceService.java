package org.example.service.interfaces;

import org.example.models.Conference;
import org.example.service.dto.ConferenceDto;
import org.example.views.ReportViewModel;

import java.util.List;

public interface ConferenceService {
    void addConference(ConferenceDto conferenceDto);

    Conference findConferenceByConfName(String name);

    List<ReportViewModel> findAllReportsFromConference(String conferenceName);
}
