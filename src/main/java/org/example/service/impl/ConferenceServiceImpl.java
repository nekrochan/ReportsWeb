package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Conference;
import org.example.repositories.ConferenceRepository;
import org.example.service.dto.ConferenceDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.FounderService;
import org.example.service.interfaces.HostService;
import org.example.utils.ValidationUtil;
import org.example.views.ReportViewModel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FounderService founderService;
    private final HostService hostService;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FounderService founderService, HostService hostService) {
        this.conferenceRepository = conferenceRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.founderService = founderService;
        this.hostService = hostService;
    }

    @Override
    public void addConference(ConferenceDto conferenceDto) {
        if (!this.validationUtil.isValid(conferenceDto)) {

            this.validationUtil
                    .violations(conferenceDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Conference conference = this.modelMapper.map(conferenceDto, Conference.class);
        conference.setFounder(this.founderService.findFounderByName(conferenceDto.getFounderName()));
        conference.setHost(this.hostService.findHostByName(conferenceDto.getHostName()));

        this.conferenceRepository
                .saveAndFlush(conference);
    }

    @Override
    public Conference findConferenceByName(String name) {
        return this.conferenceRepository.findConferenceByName(name);
    }

    @Override
    public List<ReportViewModel> findAllReportsFromConference(String conferenceName) {
        return this.conferenceRepository
                .findConferenceByName(conferenceName)
                .getReports()
                .stream()
                .map(report -> this.modelMapper.map(report, ReportViewModel.class))
                .collect(Collectors.toList());
    }

}
