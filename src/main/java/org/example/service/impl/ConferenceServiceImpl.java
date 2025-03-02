package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Conference;
import org.example.repositories.ConferenceRepository;
import org.example.service.dto.ConferenceDto;
import org.example.service.dto.ReportDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.FounderService;
import org.example.service.interfaces.HostService;
import org.example.utils.ValidationUtil;
import org.example.views.ConferenceViewModel;
import org.example.views.ReportViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "conferences", allEntries = true) // Очищаем кэш при добавлении новой конференции
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

        this.conferenceRepository.saveAndFlush(conference);
    }

    @Override
    @Cacheable(value = "conferences", key = "#name") // Кэшируем конференцию по имени
    public Conference findConferenceByConfName(String name) {
        return this.conferenceRepository.findConferenceByConfName(name);
    }

    @Override
    @Cacheable(value = "reports", key = "#conferenceName") // Кэшируем доклады по имени конференции
    public List<ReportViewModel> findAllReportsFromConference(String conferenceName) {
        return this.conferenceRepository
                .findConferenceByConfName(conferenceName)
                .getReports()
                .stream()
                .map(report -> this.modelMapper.map(report, ReportViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "reportsDto", key = "#conferenceName") // Кэшируем DTO докладов по имени конференции
    public List<ReportDto> findAllReportsFromConfDto(String conferenceName) {
        return this.conferenceRepository
                .findConferenceByConfName(conferenceName)
                .getReports()
                .stream()
                .map(report -> this.modelMapper.map(report, ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "conferences") // Кэшируем все конференции
    public List<ConferenceViewModel> findAllConferences() {
        return this.conferenceRepository
                .findAll()
                .stream()
                .map(conference -> this.modelMapper.map(conference, ConferenceViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "conferences", key = "#name") // Обновляем кэш при обновлении конференции
    public Conference updateConference(String name, ConferenceDto conferenceDto) {
        if (!this.validationUtil.isValid(conferenceDto)) {
            this.validationUtil
                    .violations(conferenceDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

        Conference conference = this.modelMapper.map(conferenceDto, Conference.class);
        conference.setFounder(this.founderService.findFounderByFounderName(conferenceDto.getFounderName()));
        conference.setHost(this.hostService.findHostByHostName(conferenceDto.getHostName()));

        return this.conferenceRepository.saveAndFlush(conference);
    }

    @Override
    @CacheEvict(value = "conferences", key = "#confName") // Удаляем конференцию из кэша
    public void deleteConference(String confName) {
        this.conferenceRepository.delete(
                this.conferenceRepository.findConferenceByConfName(confName)
        );
    }
}