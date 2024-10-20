package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Conference;
import org.example.repositories.ConferenceRepository;
import org.example.service.dto.ConferenceDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.FounderService;
import org.example.service.interfaces.HostService;
import org.example.utils.ValidationUtil;
import org.example.views.ConferenceViewModel;
import org.example.views.ReportViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FounderService founderService;
    private final HostService hostService;

    @Autowired
    public ConferenceServiceImpl(
            ConferenceRepository conferenceRepository,
            ModelMapper modelMapper,
            ValidationUtil validationUtil,
            FounderService founderService,
            HostService hostService) {
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
        conference.setFounder(this.founderService.findFounderByFounderName(conferenceDto.getFounderName()));
        conference.setHost(this.hostService.findHostByHostName(conferenceDto.getHostName()));

        this.conferenceRepository
                .saveAndFlush(conference);
    }

    @Override
    public Conference findConferenceByConfName(String name) {
        return this.conferenceRepository.findConferenceByConfName(name);
    }

    @Override
    public List<ReportViewModel> findAllReportsFromConference(String conferenceName) {
        return this.conferenceRepository
                .findConferenceByConfName(conferenceName)
                .getReports()
                .stream()
                .map(report -> this.modelMapper.map(report, ReportViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceViewModel> findAllConferences() {
        return this.conferenceRepository.
                findAll().
                stream().
                map(conference -> this.modelMapper.map(conference, ConferenceViewModel.class))
                .collect(Collectors.toList());
    }

}
