package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Founder;
import org.example.repositories.FounderRepository;
import org.example.service.dto.FounderDto;
import org.example.service.interfaces.FounderService;
import org.example.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FounderServiceImpl implements FounderService{
    private final FounderRepository founderRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public FounderServiceImpl(FounderRepository founderRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.founderRepository = founderRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addFounder(String founderName) {
        FounderDto founderDto = new FounderDto();
        founderDto.setFounderName(founderName);

        if (!this.validationUtil.isValid(founderDto)) {
            this.validationUtil
                    .violations(founderDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

        } else {
            try {
                this.founderRepository
                        .saveAndFlush(this.modelMapper.map(founderDto, Founder.class));
            } catch (Exception e) {
                System.out.println("Something went wrong!");
            }
        }
    }

    @Override
    public Founder findFounderByFounderName(String founderName) {
        return this.founderRepository.findFounderByFounderName(founderName);
    }

    @Override
    public List<FounderDto> findAllFounders() {
        return this.founderRepository.
                findAll().
                stream().
                map(founder -> this.modelMapper.map(founder, FounderDto.class))
                .collect(Collectors.toList());
    }
}
